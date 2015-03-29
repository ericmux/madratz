package com.simulation;

import org.jbox2d.dynamics.Body;

public class RotateCallable extends GameBodyCallable {

    public RotateCallable(Body body, GameWorld gameWorld) {
        super(body, gameWorld);
    }

    public RotateCallable() {
    }


    @Override
    public DecisionResult call() throws Exception {
        float w = mBody.getAngularVelocity() + 0.2f;
        mBody.setAngularVelocity(w);

        return new DecisionResult(0);
    }
}
