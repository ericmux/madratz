package com.decision;

import com.gamelogic.Actor;

public class RotateRequest extends MoveRequest {


    public RotateRequest(Actor actor, float intensity) {
        super(actor, intensity);
    }

    @Override
    public void handle() {
        mActor.getBody().setAngularVelocity(Actor.MAX_ANGULAR_SPEED*mIntensity);
    }
}
