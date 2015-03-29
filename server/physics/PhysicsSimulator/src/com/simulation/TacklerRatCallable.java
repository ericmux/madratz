package com.simulation;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class TacklerRatCallable extends GameBodyCallable {


    public TacklerRatCallable(Body body, GameWorld gameWorld) {
        super(body, gameWorld);
    }

    public TacklerRatCallable() {
        super();
    }

    @Override
    public DecisionResult call() throws Exception {

        final Body[] foundBody = new Body[1];

        mGameWorld.queryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                foundBody[0] = fixture.getBody();
                return true;
            }
        }, new AABB(mBody.getPosition().sub(new Vec2(50.0f, 50.0f)),mBody.getPosition().add(new Vec2(50.0f,50.0f))));


        if(foundBody[0] != mBody && foundBody[0] != null){
            Vec2 vel = foundBody[0].getPosition().clone().sub(mBody.getPosition());
            vel.normalize();
            vel.mulLocal(5.0f);

            mBody.setLinearVelocity(vel);
        }


        return super.call();
    }

    @Override
    public void setBody(Body body) {
        super.setBody(body);
    }

    @Override
    public void setGameWorld(GameWorld gameWorld) {
        super.setGameWorld(gameWorld);
    }
}
