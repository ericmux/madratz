package com.behaviors;

import com.decision.DecisionResult;
import com.gamelogic.Actor;

public class RotateBehavior implements Behavior {

    public RotateBehavior() {
    }

    @Override
    public DecisionResult execute(Actor actor) {
        float w = actor.getBody().getAngularVelocity() + 0.2f;
        actor.getBody().setAngularVelocity(w);

        return new DecisionResult();
    }
}
