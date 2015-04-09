package com.behaviors;

import com.gamelogic.Actor;
import com.simulation.DecisionResult;

public class NopBehavior implements Behavior {
    @Override
    public DecisionResult execute(Actor actor) {
        return new DecisionResult(0);
    }
}
