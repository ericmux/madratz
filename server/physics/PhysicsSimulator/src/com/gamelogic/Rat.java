package com.gamelogic;

import com.simulation.GameBodyCallable;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import java.util.Random;

public abstract class Rat {

    protected BodyDef mBodyDef;
    protected FixtureDef mFixtureDef;
    protected GameBodyCallable mGameBodyCallable;


    public Rat() {

        Random random = new Random();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(-25 + 50*random.nextFloat(), -25 + 50*random.nextFloat());
        bodyDef.allowSleep = false;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5.0f;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(1.0f,1.0f);
        fixtureDef.shape = polygonShape;

        mBodyDef = bodyDef;
        mFixtureDef = fixtureDef;
        mGameBodyCallable = new GameBodyCallable();
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

    public GameBodyCallable getGameBodyCallable() {
        return mGameBodyCallable;
    }

    public void setGameBodyCallable(GameBodyCallable gameBodyCallable) {
        mGameBodyCallable = gameBodyCallable;
    }
}
