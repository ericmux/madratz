package com.madratz.decision;

import com.madratz.gamelogic.Actor;
import com.madratz.gamelogic.Projectile;
import com.madratz.gamelogic.Player;
import org.jbox2d.common.Vec2;

public class ShootRequest implements ActionRequest {

    private static final float MAX_SPELL_SPEED = 3 * Player.MAX_LINEAR_SPEED;

    private final Actor mShooter;

    public ShootRequest(Actor shooter) {
        mShooter = shooter;
    }

    @Override
    public void handle() {
        Vec2 actorFront = mShooter.getBody().getWorldPoint(new Vec2(mShooter.getWidth(), 0.0f));
        float angle = mShooter.getBody().getAngle();
        Projectile projectile = new Projectile(actorFront, angle, MAX_SPELL_SPEED, 0.1f, 20);

        mShooter.getWorld().registerActor(projectile);
    }
}
