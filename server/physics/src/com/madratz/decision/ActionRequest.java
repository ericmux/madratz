package com.madratz.decision;

import com.madratz.gamelogic.Actor;

public abstract class ActionRequest {

    protected Actor mActor;

    public ActionRequest(Actor actor) {
        mActor = actor;
    }

    public abstract void handle();
}
