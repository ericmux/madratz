package com.gamelogic;

import com.simulation.MHSBehavior;

public class MHSActor extends Actor {


    public MHSActor() {
        super();
        mBehavior = new MHSBehavior();
    }
}
