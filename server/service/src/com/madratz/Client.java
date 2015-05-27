package com.madratz;

import com.madratz.jython.ScriptHandler;
import com.madratz.service.MatchParams;
import com.madratz.service.PlayerInfo;
import com.madratz.service.SimulationService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.ArrayList;

public class Client {
    public static void main(String[] args) throws TException, InterruptedException {
        TTransport transport = new TSocket("localhost", 1111);
        transport.open();

        TProtocol protocol = new TCompactProtocol(transport);
        SimulationService.Client server = new SimulationService.Client(protocol);

        MatchParams params = new MatchParams();
        ArrayList<PlayerInfo> players = new ArrayList<>();
        for(int i = 0; i < args.length; i++){
            String script = ScriptHandler.readScript(args[i]);
            players.add(new PlayerInfo(i, script));
        }
        params.setPlayers(players);


        System.out.println("Starting match with " + params.getPlayersSize() + " players.");
        long matchId = server.startMatch(params);

        System.out.println("Waiting for match result...");
        while(!server.isMatchFinished(matchId)){
            Thread.sleep(1000);
        }

        System.out.println(server.result(matchId).toString());
    }
}
