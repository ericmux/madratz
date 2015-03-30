package com.jython;

import org.python.core.PyException;
import org.python.core.PyFunction;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.net.Socket;


public class GameSocketHandler implements Closeable {
    
    public static final int GAME_PORT = 1111;


    private final Socket mSocket;
    private final PrintStream mOut;
    private final BufferedReader mIn;

    private PythonInterpreter mInterpreter;
    private PyFunction mFunction;

    public static GameSocketHandler fromSocket(Socket socket) {
        try {
            GameSocketHandler socketHandler = null;
            try {
                socketHandler = new GameSocketHandler(socket);
                return socketHandler;
            } finally {
                if (socketHandler == null) socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private GameSocketHandler(Socket socket) throws IOException {
        mSocket = socket;
        mOut = new PrintStream(mSocket.getOutputStream());
        mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
    }

    @Override
    public void close() throws IOException {
        mSocket.close();
        if (mInterpreter != null) mInterpreter.cleanup();
    }

    public void playTurn() throws IOException, PyException {
        if (mFunction == null) {
            initPython();
        }
        mFunction.__call__();
    }

    public void sendMessage(String message) {
        mOut.println(message);
    }

    private void initPython() throws IOException, PyException {
        if (mInterpreter == null) {
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec(readScript());
            mInterpreter = interpreter;
        }
        mFunction = mInterpreter.get("function", PyFunction.class);
        if (mFunction == null) {
            throw new IllegalArgumentException("Script must contain function called 'function'");
        }
    }

    private String readScript() {
        StringBuilder stringBuilder = new StringBuilder();
        int c = 0;
        do{
            try {
                c = mIn.read();
                if(c != -1) stringBuilder.append((char) c);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } while(c != -1);

        return stringBuilder.toString();
    }
}
