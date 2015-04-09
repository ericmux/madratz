package com.behaviors;

import com.decision.DecisionResult;
import com.gamelogic.Actor;

public class NopBehavior implements Behavior {
    @Override
    public DecisionResult execute(Actor actor) {
        return new DecisionResult();
    }
}
