package com.madratz.gamelogic;

import com.madratz.behavior.Behavior;
import org.jbox2d.common.Vec2;

public class Player extends Actor {

    private long mId;

    protected Player(long id, Behavior behavior) {
        super(behavior);
        mId = id;
    }

    public Player(long id, Behavior behavior, Vec2 position, float angle) {
        super(behavior, position, angle);
        mId = id;
    }

    public Player(long id, Vec2 position, float angle) {
        super(position, angle);
        mId = id;
    }

    public long getId() {
        return mId;
    }
}
