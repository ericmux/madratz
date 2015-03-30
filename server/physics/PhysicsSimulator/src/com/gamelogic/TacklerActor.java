package com.gamelogic;

import com.simulation.TackleBehavior;

public class TacklerActor extends Actor {


    public TacklerActor() {
        super();
        mBehavior = new TackleBehavior();
    }
}
