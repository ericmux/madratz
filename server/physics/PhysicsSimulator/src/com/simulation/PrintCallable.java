package com.simulation;

import org.jbox2d.dynamics.Body;

public class PrintCallable extends GameBodyCallable {

    public PrintCallable(Body body, GameWorld gameWorld) {
        super(body, gameWorld);
    }

    public PrintCallable(){
        super();
    }

    @Override
    public DecisionResult call() throws Exception {
        System.out.println("Body " + mBody.toString() + " called its update in world " + mGameWorld.toString());
        return new DecisionResult(0);
    }
}
