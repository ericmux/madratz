package com.madratz.behavior;

import com.madratz.decision.Decision;
import com.madratz.gamelogic.Actor;

public class NopBehavior implements Behavior {
    @Override
    public Decision execute(Actor actor) throws Exception {
        return new Decision();
    }
}
