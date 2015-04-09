package com.jython;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScriptHandler {

    public static final String SCRIPTS_FOLDER = "scripting/scripts/";

    public static String readScript(String scriptName){

        String scriptText = "";
        try {
            byte[] rawFile = Files.readAllBytes(Paths.get(SCRIPTS_FOLDER).resolve(scriptName));
            scriptText = new String(rawFile, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scriptText;
    }

}
