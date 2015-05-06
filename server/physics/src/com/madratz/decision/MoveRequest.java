package com.madratz.decision;

import com.madratz.gamelogic.Actor;


public abstract class MoveRequest extends ActionRequest {
    protected float mIntensity;

    public MoveRequest(Actor actor, float intensity) {
        super(actor);
        mIntensity = intensity;
    }

    @Override
    public abstract void handle();
}
