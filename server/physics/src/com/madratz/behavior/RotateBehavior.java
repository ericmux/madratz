package com.madratz.behavior;

import com.madratz.decision.Decision;
import com.madratz.decision.RotateRequest;
import com.madratz.gamelogic.Actor;

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
