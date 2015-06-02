package com.madratz.gamelogic;

import com.madratz.behavior.Behavior;
import com.madratz.behavior.NopBehavior;
import com.madratz.decision.Decision;
import com.madratz.simulation.MadratzWorld;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Actor {

    private static final Logger LOG = LoggerFactory.getLogger(Actor.class);

    public static final float MAX_LINEAR_SPEED = 10.0f;
    public static final float MAX_ANGULAR_SPEED = 15.0f;

    protected BodyDef mBodyDef;
    protected FixtureDef mFixtureDef;

    protected Behavior mBehavior;
    protected Body mBody;

    protected float mWidth;
    protected float mHP;

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
        mHP = 100.0f;

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

    public float getHP() {
        return mHP;
    }

    public void addDamage(float damage){
        mHP -= damage;
    }

    public void healDamage(float damage){
        mHP += damage;
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
}
