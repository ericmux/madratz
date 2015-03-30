package com.simulation;

import com.gamelogic.Actor;

import java.util.concurrent.Callable;


/**
 * The base callback for an agent/body. Defaults to NOP, immediately returning.
 */
public class Behavior implements Callable<DecisionResult> {

    protected Actor mActor;
    protected MadratzWorld mMadratzWorld;

    public Behavior(Actor actor, MadratzWorld madratzWorld) {
        mActor = actor;
        mMadratzWorld = madratzWorld;
    }

    public Behavior(){}

    @Override
    public DecisionResult call() throws Exception {
        return new DecisionResult(0);
    }


    public void setActor(Actor actor) {
        mActor = actor;
    }


    public void setMadratzWorld(MadratzWorld madratzWorld) {
        mMadratzWorld = madratzWorld;
    }
}
