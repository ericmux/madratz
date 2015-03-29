package com.simulation;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class MHSRatCallable extends GameBodyCallable {

    private float t;

    public MHSRatCallable(Body body, GameWorld gameWorld) {
        super(body, gameWorld);
        this.t = 0.0f;
    }

    public MHSRatCallable() {
        this.t = 0.0f;
    }


    @Override
    public DecisionResult call() throws Exception {

        Vec2 vel = new Vec2(20.0f*(float)Math.sin(2.0f*(double)t),0.0f);
        mBody.setLinearVelocity(vel);
        t += 1.0f/60.0f;

        return super.call();
    }
}
