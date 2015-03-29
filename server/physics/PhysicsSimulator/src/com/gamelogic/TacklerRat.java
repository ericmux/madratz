package com.gamelogic;

import com.simulation.TacklerRatCallable;

public class TacklerRat extends Rat{


    public TacklerRat() {
        super();
        mGameBodyCallable = new TacklerRatCallable();
    }
}
