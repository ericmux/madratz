package com.madratz.serialization;

import com.madratz.simulation.MadratzWorld;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FrameSerializer {

    private static final String SNAPSHOTS_FOLDER = "snapshots/";

    private Path mScriptsPath;

    private PrintStream mInputStream;
    private FileInputStream mOutputStream;

    public static void main(String[] args) {
        FrameSerializer serializer;
        try {
            serializer = new FrameSerializer(12);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FrameSerializer(long matchID) throws IOException {
        mScriptsPath = Paths.get(SNAPSHOTS_FOLDER).resolve(Long.toString(matchID));
    }

    public void eraseLogs() throws IOException {
        mScriptsPath = Files.walkFileTree(mScriptsPath, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.SKIP_SUBTREE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }
        });

        if(!mScriptsPath.toFile().mkdir()) throw new IOException("Scripts directory was not recreated.");
    }


    public void saveSnapshot(MadratzWorld world){





    }



}
