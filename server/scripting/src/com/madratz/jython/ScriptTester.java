package com.madratz.jython;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ScriptTester {


    public static void main(String[] args) {

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(GameSocketHandler.GAME_PORT);
            Socket connection = serverSocket.accept();


            GameSocketHandler gameSocketHandler = GameSocketHandler.fromSocket(connection);
            if(gameSocketHandler != null) {
                gameSocketHandler.callScript();
                gameSocketHandler.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
