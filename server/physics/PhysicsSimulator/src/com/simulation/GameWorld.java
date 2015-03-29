package com.simulation;

import org.jbox2d.collision.broadphase.BroadPhaseStrategy;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.IWorldPool;

import java.util.HashMap;

public class GameWorld extends World {

    private HashMap<Body,GameBodyCallable> mBodyCallbacks;


    public GameWorld(Vec2 gravity) {
        super(gravity);
        mBodyCallbacks = new HashMap<>();
    }

    public GameWorld(Vec2 gravity, IWorldPool pool) {
        super(gravity, pool);
        mBodyCallbacks = new HashMap<>();
    }

    public GameWorld(Vec2 gravity, IWorldPool argPool, BroadPhaseStrategy broadPhaseStrategy) {
        super(gravity, argPool, broadPhaseStrategy);
        mBodyCallbacks = new HashMap<>();
    }

    public Body createBody(BodyDef def) {
        Body body = super.createBody(def);

        GameBodyCallable gameBodyCallback = new GameBodyCallable(body,this);
        mBodyCallbacks.put(body,gameBodyCallback);

        return body;
    }

    public void registerCallback(Body body, GameBodyCallable callback){

        if(body == null) return;
        if(callback == null) return;

        callback.setGameWorld(this);
        callback.setBody(body);
        mBodyCallbacks.put(body,callback);
    }


    @Override
    public void step(float dt, int velocityIterations, int positionIterations) {

        for(GameBodyCallable callback: mBodyCallbacks.values()) {
            try {
                callback.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.step(dt, velocityIterations, positionIterations);
    }






}
