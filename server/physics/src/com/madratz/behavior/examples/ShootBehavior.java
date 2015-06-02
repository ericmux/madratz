package com.madratz.behavior.examples;

import com.madratz.behavior.Behavior;
import com.madratz.decision.Decision;
import com.madratz.decision.RotateRequest;
import com.madratz.decision.SpeedUpRequest;
import com.madratz.decision.SpellRequest;
import com.madratz.gamelogic.Actor;
import com.madratz.ui.SimulationTest;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class ShootBehavior implements Behavior {

    private int mBullets;
    private Body mTarget;

    public ShootBehavior() {
        mBullets = 0;
    }

    @Override
    public Decision execute(Actor actor) throws Exception {

        Decision decision = new Decision();

        final Body[] foundBody = new Body[1];

        if(mTarget == null) {
            synchronized (actor.getWorld()) {
                actor.getWorld().queryAABB(fixture -> {
                    if (fixture.getBody() != actor.getBody() && (fixture.getBody().getUserData() instanceof Actor)) {
                        foundBody[0] = fixture.getBody();
                        return false;
                    }
                    return true;
                }, new AABB(actor.getBody().getPosition().sub(new Vec2(50.0f, 50.0f)), actor.getBody().getPosition().add(new Vec2(50.0f, 50.0f))));
            }
            mTarget = foundBody[0];
        }

        if(mTarget != actor.getBody() && mTarget != null) {
            Vec2 p = actor.getBody().getPosition();
            Vec2 relPos = mTarget.getPosition().clone().sub(p);
            Vec2 vel = actor.getBody().getLinearVelocity().clone();

            if(relPos.length() < 10.0f && mBullets < 5 && Vec2.dot(relPos,vel) > 0.0f){

                SpellRequest spellRequest = new SpellRequest(actor);
                decision.addActionRequest(spellRequest);

                mBullets++;

            }

            if(relPos.length() > 3.0f) {
                relPos.normalize();
                vel.normalize();


                float theta = (float) Math.atan2(relPos.y, relPos.x) - (float) Math.atan2(vel.y, vel.x);

                float intensity = theta / (SimulationTest.TIMESTEP * Actor.MAX_ANGULAR_SPEED);

                if (Math.abs(intensity) > 1.0f) intensity = Math.signum(intensity);

                RotateRequest rotateRequest = new RotateRequest(actor, intensity);
                decision.addActionRequest(rotateRequest);
            }

        }

        SpeedUpRequest speedUpRequest = new SpeedUpRequest(actor,1.0f);
        decision.addActionRequest(speedUpRequest);


        return decision;
    }
}
