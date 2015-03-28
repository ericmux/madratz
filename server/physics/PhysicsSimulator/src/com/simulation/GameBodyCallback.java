package com.simulation;

import org.jbox2d.dynamics.Body;

public class GameBodyCallback {
    private GameBodyCallable mCallable;

    public GameBodyCallback(Body b, GameWorld world, GameBodyCallable callable) {
        this.mCallable = callable;
        this.mCallable.setBody(b);
        this.mCallable.setGameWorld(world);
    }

    public void update(){
        try {
            this.mCallable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
