package com.simulation;

public class RotateBehavior extends Behavior {

    public RotateBehavior() {
    }


    @Override
    public DecisionResult call() throws Exception {
        float w = mActor.getBody().getAngularVelocity() + 0.2f;
        mActor.getBody().setAngularVelocity(w);

        return new DecisionResult(0);
    }
}
