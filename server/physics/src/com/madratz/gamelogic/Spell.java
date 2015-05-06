package com.madratz.gamelogic;

import com.madratz.behavior.NopBehavior;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Spell extends Actor {

    public static final float MAX_SPELL_SPEED = 20.0f;

    private float mSpeed;

    private float mHitDamage;

    public Spell(Vec2 position, float angle, float speed, float radius, float hitDamage) {
        super(new NopBehavior());


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        bodyDef.allowSleep = false;
        bodyDef.angle = angle;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5.0f;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;


        mWidth = 2*fixtureDef.shape.getRadius();


        mBodyDef = bodyDef;
        mFixtureDef = fixtureDef;
        mSpeed = speed;
        mBody = null;
        mHitDamage = hitDamage;

    }

    @Override
    public void handleCollision(Actor actor) {
        if(actor != null && actor.getClass() == Actor.class){
            actor.addDamage(mHitDamage);
            if(actor.getHP() < 0.0f){
                getWorld().destroyActor(actor);
            }
        }

        getWorld().destroyActor(this);
    }

    public float getSpeed() {
        return mSpeed;
    }


    public void setSpeed(float speed) {
        mSpeed = speed;
    }

    public float getHitDamage() {
        return mHitDamage;
    }
}
