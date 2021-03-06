package com.madratz.gamelogic.player;

import com.madratz.behavior.Behavior;
import com.madratz.gamelogic.Actor;
import org.jbox2d.common.Vec2;

public class Player extends Actor {

    public static final float MAX_LINEAR_SPEED = 10.0f;
    public static final float MAX_ANGULAR_SPEED = 7.5f;

    private final String mId;
    private final Weapon mWeapon;

    private float mHP = maxHP();

    public Player(String id, Behavior behavior, Vec2 position, float angle, Weapon weapon) {
        super(behavior, position, angle);
        mId = id;
        mWeapon = weapon;
        mWeapon.setOwner(this);
    }

    public Weapon getWeapon() {
        return mWeapon;
    }

    public float getHP() {
        return mHP;
    }

    public float maxHP() {
        return 100.0f;
    }

    public void inflictDamage(float damage) {
        mHP -= damage;

        if (mHP <= 0.0f) {
            mHP = 0;
            getWorld().destroyActor(this);
        }
    }

    public void healAmount(float healthPoints) {
        mHP += healthPoints;
        if (mHP > 100) mHP = 100.0f;
    }

    public String getId() {
        return mId;
    }

    @Override
    public com.madratz.serialization.Actor toThrift() {
        com.madratz.serialization.Actor actor = super.toThrift();
        actor.setId(mId);
        actor.setHp(mHP);
        return actor;
    }

    @Override
    public String toString() {
        return "Player " + mId;
    }
}
