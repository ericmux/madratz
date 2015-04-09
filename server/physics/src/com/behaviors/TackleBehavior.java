package com.behaviors;

import com.decision.DecisionResult;
import com.gamelogic.Actor;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class TackleBehavior implements Behavior {


    public TackleBehavior() {
        super();
    }

    @Override
    public DecisionResult execute(Actor actor) {
        final Body[] foundBody = new Body[1];

        actor.getWorld().queryAABB(fixture -> {
            if(fixture.getBody() != actor.getBody()) {
                foundBody[0] = fixture.getBody();
            }
            return true;
        }, new AABB(actor.getBody().getPosition().sub(new Vec2(50.0f, 50.0f)), actor.getBody().getPosition().add(new Vec2(50.0f,50.0f))));


        if(foundBody[0] != actor.getBody() && foundBody[0] != null){
            Vec2 vel = foundBody[0].getPosition().clone().sub(actor.getBody().getPosition());
            vel.normalize();
            vel.mulLocal(5.0f);

            actor.getBody().setLinearVelocity(vel);
        }


        return new DecisionResult();
    }
}
