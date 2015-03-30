package com.behaviors;

import com.simulation.DecisionResult;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class ShootBehavior extends Behavior {


    private int mbullets;

    public ShootBehavior() {
        mbullets = 0;
    }


    @Override
    public DecisionResult call() throws Exception {

        final Body[] foundBody = new Body[1];

        mMadratzWorld.queryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                if(fixture.getBody() != mActor.getBody()) {
                    foundBody[0] = fixture.getBody();
                }
                return true;
            }
        }, new AABB(mActor.getBody().getPosition().sub(new Vec2(50.0f, 50.0f)), mActor.getBody().getPosition().add(new Vec2(50.0f,50.0f))));


        if(foundBody[0] != mActor.getBody() && foundBody[0] != null){

            Vec2 p = mActor.getBody().getPosition();

            Vec2 vel = foundBody[0].getPosition().clone().sub(p);

            if(vel.length() < 10.0f && mbullets < 5 && Vec2.dot(vel,mActor.getBody().getLinearVelocity()) > 0.0f){

                Vec2 bulletPos = new Vec2(p.x,p.y + mActor.getBody().getFixtureList().getShape().getRadius());
                Vec2 bulletVel = foundBody[0].getPosition().clone().sub(bulletPos);

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyType.DYNAMIC;
                bodyDef.position.set(bulletPos.x,bulletPos.y);
                bodyDef.allowSleep = false;
                bodyDef.bullet = true;

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.density = 5.0f;
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(0.1f,0.1f);
                fixtureDef.shape = polygonShape;
                fixtureDef.filter.categoryBits = 0x0;
                fixtureDef.filter.maskBits = 0x0;

                Body bullet = mMadratzWorld.createBody(bodyDef);
                bullet.createFixture(fixtureDef);


                bulletVel.normalize();
                bulletVel.mulLocal(200.0f);

                bullet.setLinearVelocity(bulletVel);

                mbullets++;

            }


            vel.normalize();
            vel.mulLocal(5.0f);


            mActor.getBody().setLinearVelocity(vel);
        }




        return super.call();
    }
}
