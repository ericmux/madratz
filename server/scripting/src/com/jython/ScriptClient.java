package com.jython;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScriptClient {

    public static String SCRIPT_NAME = "scripting/scripts/hello_world.py";

    private Socket mClientSocket;
    private String mScriptText;


    private PrintStream mOut;


    public ScriptClient() {

        try {
            byte[] rawFile = Files.readAllBytes(Paths.get(SCRIPT_NAME));
            mScriptText = new String(rawFile, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(mScriptText);

    }

    public void connect(){
        try {
            mClientSocket = new Socket(InetAddress.getLocalHost().getHostName(), GameSocketHandler.GAME_PORT);
            mOut = new PrintStream(mClientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mOut.print(mScriptText);
    }


    public static void main(String[] args) {
        ScriptClient client = new ScriptClient();
        client.connect();
    }
}
