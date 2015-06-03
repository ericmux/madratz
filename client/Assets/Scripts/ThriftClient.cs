using UnityEngine;
using System.Collections;
using Thrift;
using Thrift.Protocol;
using Thrift.Server;
using Thrift.Transport;

public class ThriftClient {



	/*Map<String, String> flags = parseFlags(args);
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
	System.out.println("Match finished! Result: " + server.result(matchId));*/

	public static void main() {
		/*TTransport transport = new TStreamTransport(is, os);
		TProtocol protocol = new TCompactProtocol(transport);

		for (int i = 0; i < 10000; i++) {
			Snapshot snapshot = new Snapshot();
			snapshot.Actors.Add(new Actor
			snapshot.Write(protocol);
		}*/
	}
}
