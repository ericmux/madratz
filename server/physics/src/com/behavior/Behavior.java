package com.behavior;

import com.decision.Decision;
import com.gamelogic.Actor;


/**
 * Represents the reasoning used by an agent (Actor) at every frame.
 */
public interface Behavior {
    Decision execute(Actor actor);
}
