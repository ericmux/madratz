package com.gamelogic;

import com.simulation.MHSRatCallable;

public class MHSRat extends Rat {


    public MHSRat() {
        super();
        mGameBodyCallable = new MHSRatCallable();
    }
}
