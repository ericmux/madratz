package com.simulation;

import org.jbox2d.common.Vec2;

public class MHSBehavior extends Behavior {

    private float t;

    public MHSBehavior() {
        this.t = 0.0f;
    }


    @Override
    public DecisionResult call() throws Exception {

        Vec2 vel = new Vec2(20.0f*(float)Math.sin(2.0f*(double)t),0.0f);
        mActor.getBody().setLinearVelocity(vel);
        t += 1.0f/60.0f;

        return super.call();
    }
}
