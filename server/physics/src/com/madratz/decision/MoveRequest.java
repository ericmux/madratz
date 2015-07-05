package com.madratz.decision;

import com.madratz.gamelogic.Actor;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.function.Consumer;


public class MoveRequest implements ActionRequest {

    public static MoveRequest forVelocity(Actor actor, float intensity) {
        return forLocalVelocity(actor, new Vec2(intensity, 0));
    }

    public static MoveRequest forLocalVelocity(Actor actor, Vec2 localVelocity) {
        return forWorldVelocity(actor, actor.getBody().getWorldVector(localVelocity));
    }

    public static MoveRequest forWorldVelocity(Actor actor, Vec2 worldVelocity) {
        return new MoveRequest(actor, b -> b.setLinearVelocity(worldVelocity));
    }

    public static MoveRequest forRotation(Actor actor, float intensity) {
        return new MoveRequest(actor, b -> b.setAngularVelocity(intensity));
    }

    private final Actor mActor;
    private final Consumer<Body> mApplier;

    private MoveRequest(Actor actor, Consumer<Body> applier) {
        mActor = actor;
        mApplier = applier;
    }

    @Override
    public void handle() {
        mApplier.accept(mActor.getBody());
    }
}
