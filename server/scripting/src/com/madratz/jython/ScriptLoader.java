package com.madratz.jython;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScriptLoader {

    public static final String SCRIPTS_FOLDER = "scripting/scripts/";

    public static String readScript(String scriptName) {
        try {
            byte[] rawFile = Files.readAllBytes(Paths.get(SCRIPTS_FOLDER, scriptName));
            return new String(rawFile, Charset.defaultCharset());
        } catch (IOException e) {
            System.err.println("Failed to load script file: " + scriptName);
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
