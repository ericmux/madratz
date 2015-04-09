package com.behaviors;

import com.gamelogic.Actor;
import com.simulation.DecisionResult;

public class RotateBehavior implements Behavior {

    public RotateBehavior() {
    }

    @Override
    public DecisionResult execute(Actor actor) {
        float w = actor.getBody().getAngularVelocity() + 0.2f;
        actor.getBody().setAngularVelocity(w);

        return new DecisionResult(0);
    }
}
