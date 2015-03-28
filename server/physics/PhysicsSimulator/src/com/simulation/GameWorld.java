package com.simulation;

import org.jbox2d.collision.broadphase.BroadPhaseStrategy;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.IWorldPool;

import java.util.ArrayList;

public class GameWorld extends World {

    private ArrayList<GameBodyCallback> mBodyCallbacks;


    public GameWorld(Vec2 gravity) {
        super(gravity);
        mBodyCallbacks = new ArrayList<GameBodyCallback>();
    }

    public GameWorld(Vec2 gravity, IWorldPool pool) {
        super(gravity, pool);
        mBodyCallbacks = new ArrayList<GameBodyCallback>();
    }

    public GameWorld(Vec2 gravity, IWorldPool argPool, BroadPhaseStrategy broadPhaseStrategy) {
        super(gravity, argPool, broadPhaseStrategy);
        mBodyCallbacks = new ArrayList<GameBodyCallback>();
    }

    public Body createBody(BodyDef def, GameBodyCallable callback) {
        Body body = super.createBody(def);
        GameBodyCallback gameBodyCallback = new GameBodyCallback(body,this, callback);
        mBodyCallbacks.add(gameBodyCallback);

        return body;
    }

    @Override
    public void step(float dt, int velocityIterations, int positionIterations) {

        for(GameBodyCallback callback: mBodyCallbacks) callback.update();

        super.step(dt, velocityIterations, positionIterations);
    }



}
