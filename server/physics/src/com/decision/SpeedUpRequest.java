package com.decision;

import com.gamelogic.Actor;

public class SpeedUpRequest extends MoveRequest {


    public SpeedUpRequest(Actor actor, float intensity) {
        super(actor, intensity);
    }

    @Override
    public void handle() {
        mActor.getBody().getLinearVelocity().normalize();
        mActor.getBody().getLinearVelocity().mulLocal(Actor.MAX_LINEAR_SPEED*mIntensity);
    }
}
