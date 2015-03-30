package com.simulation;

import com.behaviors.Behavior;
import com.gamelogic.Actor;
import org.jbox2d.collision.broadphase.BroadPhaseStrategy;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.IWorldPool;

import java.util.HashSet;

public class MadratzWorld extends World {

    private HashSet<Actor> mActiveActors;


    public MadratzWorld(Vec2 gravity) {
        super(gravity);
        mActiveActors = new HashSet<>();
    }

    public MadratzWorld(Vec2 gravity, IWorldPool pool) {
        super(gravity, pool);
        mActiveActors = new HashSet<>();
    }

    public MadratzWorld(Vec2 gravity, IWorldPool argPool, BroadPhaseStrategy broadPhaseStrategy) {
        super(gravity, argPool, broadPhaseStrategy);
        mActiveActors = new HashSet<>();
    }

    public void registerActor(Actor actor){

        if(actor == null) return;

        Body body = createBody(actor.getBodyDef());
        body.createFixture(actor.getFixtureDef());
        body.setUserData(actor);

        actor.setBody(body);

        Behavior behavior = actor.getBehavior();
        behavior.setMadratzWorld(this);
        behavior.setActor(actor);

        mActiveActors.add(actor);
    }


    @Override
    public void step(float dt, int velocityIterations, int positionIterations) {

        for(Actor actor: mActiveActors) {
            try {
                actor.getBehavior().call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.step(dt, velocityIterations, positionIterations);
    }






}
