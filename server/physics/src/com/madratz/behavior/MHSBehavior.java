package com.madratz.behavior;

import com.madratz.decision.Decision;
import com.madratz.decision.SpeedUpRequest;
import com.madratz.gamelogic.Actor;

public class MHSBehavior implements Behavior {

    private float t;

    public MHSBehavior() {
        this.t = 0.0f;
    }

    @Override
    public Decision execute(Actor actor) {
        Decision decision = new Decision();

        SpeedUpRequest speedUpRequest = new SpeedUpRequest(actor,(float)Math.sin(2.0f*(double)t));
        decision.addActionRequest(speedUpRequest);

        t += 1.0f/60.0f;

        return decision;
    }
}
