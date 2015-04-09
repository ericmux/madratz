package com.decision;

import com.gamelogic.Actor;


public abstract class MoveRequest implements ActionRequest{
    protected Actor mActor;
    protected float mIntensity;

    public MoveRequest(Actor actor, float intensity) {
        mActor = actor;
        mIntensity = intensity;
    }

    @Override
    public abstract void handle();
}
