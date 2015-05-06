package com.madratz.decision;

import com.madratz.gamelogic.Actor;
import org.jbox2d.common.Vec2;

public class SpeedUpRequest extends MoveRequest {


    public SpeedUpRequest(Actor actor, float intensity) {
        super(actor, intensity);
    }

    @Override
    public void handle() {
        Vec2 vel = mActor.getBody().getLinearVelocity();
        vel.set(mActor.getBody().getWorldVector(new Vec2(1.0f,0.0f)))
                                .mulLocal(Actor.MAX_LINEAR_SPEED*mIntensity);
    }
}
