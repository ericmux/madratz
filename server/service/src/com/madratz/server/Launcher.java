package com.madratz.server;

import com.madratz.service.SimulationService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Launcher {

    public static void main(String[] args) {
        Map<String, String> flags = parseFlags(args);

        SimulationService.Iface handler = new SimulationServiceImpl();
        SimulationService.Processor processor = new SimulationService.Processor<>(handler);

        int port = Integer.parseInt(flags.get("port"));
        System.out.println("Starting simulation server on port " + port + "!");
        startServer(processor, port);
    }

    private static void startServer(TProcessor processor, int port) {
        try {
            TServerTransport serverTransport = new TServerSocket(port);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                    .minWorkerThreads(5)
                    .maxWorkerThreads(60)
                    .protocolFactory(new TCompactProtocol.Factory())
                    .processor(processor));

            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> parseFlags(String[] args) {
        return Arrays.stream(args)
                .filter(s -> s.matches("--\\w+=.+"))
                .map(s -> s.substring(2).split("=", 2))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }
}
