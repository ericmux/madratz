package com.madratz.serialization;

import com.madratz.simulation.MadratzWorld;
import org.jbox2d.serialization.pb.PbSerializer;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FrameSerializer {

    private static final String SNAPSHOTS_FOLDER = "snapshots/";

    private Path mScriptsPath;
    private PbSerializer mSerializer;

    public FrameSerializer() throws IOException {
        mSerializer = new PbSerializer();
        mScriptsPath = Paths.get(SNAPSHOTS_FOLDER);
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
