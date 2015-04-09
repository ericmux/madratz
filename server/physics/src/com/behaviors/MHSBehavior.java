package com.behaviors;

import com.decision.DecisionResult;
import com.gamelogic.Actor;
import org.jbox2d.common.Vec2;

public class MHSBehavior implements Behavior {

    private float t;

    public MHSBehavior() {
        this.t = 0.0f;
    }

    @Override
    public DecisionResult execute(Actor actor) {
        Vec2 vel = new Vec2(20.0f*(float)Math.sin(2.0f*(double)t),0.0f);
        actor.getBody().setLinearVelocity(vel);
        t += 1.0f/60.0f;

        return new DecisionResult();
    }
}
