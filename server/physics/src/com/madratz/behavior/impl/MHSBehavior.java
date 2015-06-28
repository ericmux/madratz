package com.madratz.behavior.impl;

import com.madratz.behavior.Behavior;
import com.madratz.decision.Decision;
import com.madratz.decision.MoveRequest;
import com.madratz.gamelogic.Actor;

public class MHSBehavior implements Behavior {

    private float t;

    public MHSBehavior() {
        this.t = 0.0f;
    }

    @Override
    public Decision execute(Actor actor) throws Exception {
        Decision decision = new Decision();

        MoveRequest velocityChangeRequest = MoveRequest.forVelocity(actor,(float) Math.sin(2.0f * t));
        decision.addActionRequest(velocityChangeRequest);

        t += 1.0f/60.0f;

        return decision;
    }
}
