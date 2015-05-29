package com.madratz;

import com.madratz.jython.ScriptLoader;
import com.madratz.service.MatchParams;
import com.madratz.service.PlayerInfo;
import com.madratz.service.SimulationService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Client {

    public static void main(String[] args) throws TException, InterruptedException {
        Map<String, String> flags = parseFlags(args);
        String[] scripts = flags.get("scripts").split(","); // add a flag like --scripts=file1.py,file2.py,file3.py...
        String serverAddress = flags.get("address");
        int port = Integer.parseInt(flags.get("port"));

        TTransport transport = new TSocket(serverAddress, port);
        transport.open();

        TProtocol protocol = new TCompactProtocol(transport);
        SimulationService.Client server = new SimulationService.Client(protocol);
        System.out.println("Connected to server at " + serverAddress + ":" + port);

        MatchParams params = new MatchParams();
        for (int i = 0; i < scripts.length; i++) {
            params.addToPlayers(new PlayerInfo(i + 1, ScriptLoader.readScript(scripts[i])));
        }

        System.out.println("Starting match with " + params.getPlayersSize() + " players");
        long matchId = server.startMatch(params);
        System.out.println("Match id is " + matchId + ". Waiting for match to finish.");
        while (!server.isMatchFinished(matchId)) {
            Thread.sleep(100);
        }
        System.out.println("Match finished! Result: " + server.result(matchId));
    }

    private static Map<String, String> parseFlags(String[] args) {
        return Arrays.stream(args)
                .filter(s -> s.matches("--\\w+=.+"))
                .map(s -> s.substring(2).split("=", 2))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }
}
