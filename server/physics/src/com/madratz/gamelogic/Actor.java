package com.madratz.gamelogic;

import com.madratz.behavior.Behavior;
import com.madratz.behavior.impl.NopBehavior;
import com.madratz.decision.Decision;
import com.madratz.serialization.Thriftalizable;
import com.madratz.serialization.Vector2;
import com.madratz.simulation.MadratzWorld;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Actor implements Thriftalizable {

    private static final Logger LOG = LoggerFactory.getLogger(Actor.class);

    protected BodyDef mBodyDef;
    protected FixtureDef mFixtureDef;

    protected Behavior mBehavior;
    protected Body mBody;

    protected float mWidth;

    protected Actor(Behavior behavior) {
        mBehavior = behavior;
    }

    public Actor(Behavior behavior, Vec2 position, float angle) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        bodyDef.allowSleep = false;
        bodyDef.angle = angle;

        mWidth = 2*1.0f;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5.0f;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(1.0f,1.0f);
        fixtureDef.shape = polygonShape;

        mBodyDef = bodyDef;
        mFixtureDef = fixtureDef;
        mBehavior = behavior;
    }

    public Actor(Vec2 position, float angle) {
        this(new NopBehavior(), position, angle);
    }

    public Decision executeBehavior() {
        try {
            return mBehavior.execute(this);
        } catch (Exception e) {
            LOG.warn("Python script for " + this + " threw exception: " + e, e);
        }
        return new Decision();
    }

    public void handleCollision(Actor actor) {}

    public float getWidth() {
        return mWidth;
    }

    public Body getBody() {
        return mBody;
    }

    public MadratzWorld getWorld() {
        return (MadratzWorld)mBody.getWorld();
    }

    public void setBody(Body body) {
        mBody = body;
    }

    public BodyDef getBodyDef() {
        return mBodyDef;
    }

    public FixtureDef getFixtureDef() {
        return mFixtureDef;
    }

    public Behavior getBehavior() {
        return mBehavior;
    }

    public void setBehavior(Behavior behavior) {
        mBehavior = behavior;
    }

    @Override
    public com.madratz.serialization.Actor toThrift() {
        com.madratz.serialization.Actor serializedActor = new com.madratz.serialization.Actor();
        Vec2 position = mBody.getPosition();
        serializedActor.setPosition(new Vector2(position.x, position.y));
        serializedActor.setAngle(mBody.getAngle());
        serializedActor.setWidth(mWidth);

        return serializedActor;
    }
}
