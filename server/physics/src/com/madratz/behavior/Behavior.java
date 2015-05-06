package com.madratz.behavior;

import com.madratz.decision.Decision;
import com.madratz.gamelogic.Actor;


/**
 * Represents the reasoning used by an agent (Actor) at every frame.
 */
public interface Behavior {
    Decision execute(Actor actor);
}
