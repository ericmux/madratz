package com.madratz;

import com.madratz.jython.ScriptLoader;
import com.madratz.serialization.Snapshot;
import com.madratz.service.MatchParams;
import com.madratz.service.PlayerInfo;
import com.madratz.service.SimulationService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Client {

    public static void main(String[] args) throws TException, InterruptedException, IOException {
        Map<String, String> flags = parseFlags(args);
        String[] scripts = flags.get("scripts").split(","); // add a flag like --scripts=file1.py,file2.py,file3.py...
        String serverAddress = flags.get("address");
        int port = Integer.parseInt(flags.get("port"));

        try (TTransport transport = new TSocket(serverAddress, port)) {
            transport.open();
            SimulationService.Client server = new SimulationService.Client(new TCompactProtocol(transport));
            System.out.println("Connected to server at " + serverAddress + ":"  + port);

            MatchParams params = new MatchParams();
            for (int i = 0; i < scripts.length; i++) {
                params.addToPlayers(new PlayerInfo(i + 1, ScriptLoader.readScript(scripts[i])));
            }

            System.out.println("Starting match with " + params.getPlayersSize() + " players");
            String matchId = params.matchId = generateId();
            server.startMatch(params);

            System.out.println("Match id is " + matchId + ". Waiting for match to finish.");
            while (!server.isMatchFinished(matchId)) {
                Thread.sleep(100);
            }
            System.out.println("Match finished! Result: " + server.result(matchId));

            String outputFile = flags.get("out");
            if (outputFile != null) {
                System.out.println("Writing to output file " + outputFile + "...");
                try (OutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
                     TTransport tout = new TIOStreamTransport(os)) {
                    tout.open();
                    TProtocol writer = new TCompactProtocol(tout);
                    for (Snapshot s : server.snapshots(matchId)) {
                        s.write(writer);
                    }
                }
            }
        }
    }

    private static String generateId() {
        byte[] stringBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(stringBytes);
        return String.format("%032x", new BigInteger(1, stringBytes));
    }

    private static Map<String, String> parseFlags(String[] args) {
        return Arrays.stream(args)
                .filter(s -> s.matches("--\\w+=.+"))
                .map(s -> s.substring(2).split("=", 2))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }
}
