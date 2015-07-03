package com.madratz.gamelogic.player;

import com.madratz.decision.Decision;

public abstract class Weapon {

    private Player mOwner;

    void setOwner(Player owner) {
        if (mOwner != null) throw new IllegalStateException("Weapon cannot change owner");
        mOwner = owner;
    }

    protected Player getOwner() {
        if (mOwner == null) throw new IllegalStateException("Owner of weapon must be set");
        return mOwner;
    }

    public abstract Weapon.Interface getInterface(Decision decision);

    public interface Interface { }
}
