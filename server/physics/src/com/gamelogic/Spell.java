package com.gamelogic;

import com.behavior.NopBehavior;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Spell extends Actor {

    public static final float MAX_SPELL_SPEED = 20.0f;

    private float mSpeed;

    public Spell(Vec2 position, float angle, float speed, float radius) {
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

    }


    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float speed) {
        mSpeed = speed;
    }
}
