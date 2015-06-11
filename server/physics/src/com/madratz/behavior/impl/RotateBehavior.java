package com.madratz.behavior.impl;

import com.madratz.behavior.Behavior;
import com.madratz.decision.Decision;
import com.madratz.decision.MoveRequest;
import com.madratz.gamelogic.Actor;

public class RotateBehavior implements Behavior {

    public RotateBehavior() {
    }

    @Override
    public Decision execute(Actor actor) throws Exception {
        Decision decision = new Decision();
        decision.addActionRequest(MoveRequest.forRotation(actor, 1.0f));
        return decision;
    }
}
