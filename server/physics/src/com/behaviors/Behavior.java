package com.behaviors;

import com.gamelogic.Actor;
import com.simulation.DecisionResult;


/**
 * The base callback for an agent/body. Defaults to NOP, immediately returning.
 */
public interface Behavior {
    public DecisionResult execute(Actor actor);
}
