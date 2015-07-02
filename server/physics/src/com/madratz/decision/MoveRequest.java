package com.madratz.decision;

import com.madratz.gamelogic.Actor;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.function.Consumer;


public class MoveRequest implements ActionRequest {

    public static MoveRequest forVelocity(Actor actor, float intensity) {
        Vec2 vel = actor.getBody().getWorldVector(new Vec2(intensity, 0));
        return new MoveRequest(actor, b -> b.setLinearVelocity(vel));
    }

    public static MoveRequest forRotation(Actor actor, float intensity) {
        return new MoveRequest(actor, b -> b.setAngularVelocity(intensity));
    }

    private final Actor mActor;
    private final Consumer<Body> mApplier;

    public MoveRequest(Actor actor, Consumer<Body> applier) {
        mActor = actor;
        mApplier = applier;
    }

    @Override
    public void handle() {
        mApplier.accept(mActor.getBody());
    }
}
