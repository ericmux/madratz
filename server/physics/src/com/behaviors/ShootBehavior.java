package com.behaviors;

import com.gamelogic.Actor;
import com.simulation.DecisionResult;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class ShootBehavior implements Behavior {


    private int mBullets;

    public ShootBehavior() {
        mBullets = 0;
    }

    @Override
    public DecisionResult execute(Actor actor) {
        final Body[] foundBody = new Body[1];

        actor.getWorld().queryAABB(fixture -> {
            if(fixture.getBody() != actor.getBody() && (fixture.getBody().getUserData() instanceof Actor)) {
                foundBody[0] = fixture.getBody();
            }
            return true;
        }, new AABB(actor.getBody().getPosition().sub(new Vec2(50.0f, 50.0f)), actor.getBody().getPosition().add(new Vec2(50.0f,50.0f))));

        if(foundBody[0] != actor.getBody() && foundBody[0] != null) {
            Vec2 p = actor.getBody().getPosition();
            Vec2 vel = foundBody[0].getPosition().clone().sub(p);

            if(vel.length() < 10.0f && mBullets < 5 && Vec2.dot(vel,actor.getBody().getLinearVelocity()) > 0.0f){

                Vec2 bulletPos = new Vec2(p.x,p.y + actor.getBody().getFixtureList().getShape().getRadius());
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

                Body bullet = actor.getWorld().createBody(bodyDef);
                bullet.createFixture(fixtureDef);


                bulletVel.normalize();
                bulletVel.mulLocal(200.0f);

                bullet.setLinearVelocity(bulletVel);

                mBullets++;

            }


            vel.normalize();
            vel.mulLocal(5.0f);


            actor.getBody().setLinearVelocity(vel);
        }




        return new DecisionResult(0);
    }
}
