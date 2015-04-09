package com.gamelogic;

import com.behaviors.Behavior;
import com.behaviors.NopBehavior;
import com.simulation.DecisionResult;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Actor {

    protected BodyDef mBodyDef;
    protected FixtureDef mFixtureDef;

    protected Behavior mBehavior;
    protected Body mBody;

    public Actor(Behavior behavior, Vec2 position, float angle) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        bodyDef.allowSleep = false;
        bodyDef.angle = angle;

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

    public Body getBody() {
        return mBody;
    }

    public World getWorld() {
        return mBody.getWorld();
    }

    public void setBody(Body body) {
        mBody = body;
    }

    public BodyDef getBodyDef() {
        return mBodyDef;
    }

    public void setBodyDef(BodyDef bodyDef) {
        mBodyDef = bodyDef;
    }

    public FixtureDef getFixtureDef() {
        return mFixtureDef;
    }

    public void setFixtureDef(FixtureDef fixtureDef) {
        mFixtureDef = fixtureDef;
    }

    public Behavior getBehavior() {
        return mBehavior;
    }

    public void setBehavior(Behavior behavior) {
        mBehavior = behavior;
    }

    public DecisionResult executeBehavior() {
        return mBehavior.execute(this);
    }
}
