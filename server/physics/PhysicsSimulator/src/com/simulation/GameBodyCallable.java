package com.simulation;

import org.jbox2d.dynamics.Body;

import java.util.concurrent.Callable;


/**
 * The base callback for an agent/body. Defaults to NOP, immediately returning.
 */
public class GameBodyCallable implements Callable<DecisionResult> {

    protected Body mBody;
    protected GameWorld mGameWorld;

    public GameBodyCallable(Body body, GameWorld gameWorld) {
        mBody = body;
        mGameWorld = gameWorld;
    }

    public GameBodyCallable(){}

    @Override
    public DecisionResult call() throws Exception {
        return new DecisionResult(0);
    }


    public void setBody(Body body) {
        mBody = body;
    }


    public void setGameWorld(GameWorld gameWorld) {
        mGameWorld = gameWorld;
    }
}
