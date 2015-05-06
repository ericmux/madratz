package com.madratz.wrappers;

import com.madratz.decision.Decision;
import com.madratz.decision.RotateRequest;
import com.madratz.decision.SpeedUpRequest;
import com.madratz.decision.SpellRequest;
import com.madratz.gamelogic.Actor;
import com.madratz.simulation.MadratzWorld;
import org.jbox2d.dynamics.Body;

public class ActorWrapper {

    private Actor mActor;
    private Decision mDecision;

    public ActorWrapper(Actor actor, Decision decision) {
        mActor = actor;
        mDecision = decision;
    }

    public void speedUp(float intensity){
        mDecision.addActionRequest(new SpeedUpRequest(mActor,intensity));
    }

    public void rotate(float intensity){
        mDecision.addActionRequest(new RotateRequest(mActor,intensity));
    }

    public void shoot(){
        mDecision.addActionRequest(new SpellRequest(mActor));
    }

    public Decision getDecision() {
        return mDecision;
    }

    public Body __body__() {
        return mActor.getBody();
    }

    public MadratzWorld __world__() {
        return mActor.getWorld();
    }
}
