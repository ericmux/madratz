package com.behavior;

import com.decision.Decision;
import com.decision.RotateRequest;
import com.gamelogic.Actor;

public class RotateBehavior implements Behavior {

    public RotateBehavior() {
    }

    @Override
    public Decision execute(Actor actor) {
        Decision decision = new Decision();
        decision.addActionRequest(new RotateRequest(actor,1.0f));
        return decision;
    }
}
