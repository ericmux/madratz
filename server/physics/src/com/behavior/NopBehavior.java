package com.behavior;

import com.decision.Decision;
import com.gamelogic.Actor;

public class NopBehavior implements Behavior {
    @Override
    public Decision execute(Actor actor) {
        return new Decision();
    }
}
