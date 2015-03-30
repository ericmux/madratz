package com.behaviors;

import com.gamelogic.Actor;
import com.simulation.DecisionResult;
import com.simulation.MadratzWorld;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class TackleBehavior extends Behavior {


    public TackleBehavior() {
        super();
    }

    @Override
    public DecisionResult call() throws Exception {

        final Body[] foundBody = new Body[1];

        mMadratzWorld.queryAABB(fixture -> {
            if(fixture.getBody() != mActor.getBody()) {
                foundBody[0] = fixture.getBody();
            }
            return true;
        }, new AABB(mActor.getBody().getPosition().sub(new Vec2(50.0f, 50.0f)), mActor.getBody().getPosition().add(new Vec2(50.0f,50.0f))));


        if(foundBody[0] != mActor.getBody() && foundBody[0] != null){
            Vec2 vel = foundBody[0].getPosition().clone().sub(mActor.getBody().getPosition());
            vel.normalize();
            vel.mulLocal(5.0f);

            mActor.getBody().setLinearVelocity(vel);
        }


        return super.call();
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);
    }

    @Override
    public void setMadratzWorld(MadratzWorld madratzWorld) {
        super.setMadratzWorld(madratzWorld);
    }
}
