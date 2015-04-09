package com.behaviors;

import com.decision.DecisionResult;
import com.gamelogic.Actor;


/**
 * The base callback for an agent/body. Defaults to NOP, immediately returning.
 */
public interface Behavior {
    DecisionResult execute(Actor actor);
}
