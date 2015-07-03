package com.madratz.gamelogic.player;

import com.madratz.decision.Decision;
import com.madratz.gamelogic.Projectile;
import org.jbox2d.common.Vec2;

public class RangedWeapon extends Weapon {

    private final float mCooldownSec;
    private final float mBaseDamage;
    private final float mProjectileSpeed;

    private float mNextShotAvailableTime = Float.MIN_VALUE;

    public RangedWeapon(float attackSpeed, float baseDamage, float projectileSpeed) {
        mCooldownSec = 1 / attackSpeed;
        mBaseDamage = baseDamage;
        mProjectileSpeed = projectileSpeed;
    }

    @Override
    public Weapon.Interface getInterface(Decision decision) {
        return new Interface(decision);
    }

    private float currentTimeSec() {
        return getOwner().getWorld().getElapsedTime();
    }

    public class Interface implements Weapon.Interface {

        private final Decision mDecision;

        public Interface(Decision decision) {
            mDecision = decision;
        }

        public void fire() {
            if (!is_ready()) throw new IllegalStateException("Unable to shoot before weapon is ready");
            mNextShotAvailableTime = currentTimeSec() + mCooldownSec;

            mDecision.addActionRequest(() -> {
                Player shooter = getOwner();
                Vec2 shooterFront = shooter.getBody().getWorldPoint(new Vec2(shooter.getWidth(), 0.0f));

                float angle = shooter.getBody().getAngle();
                Projectile projectile = new Projectile(shooterFront, angle, mProjectileSpeed, 0.1f, mBaseDamage);

                shooter.getWorld().registerActor(projectile);
            });
        }

        public boolean is_ready() {
            return currentTimeSec() >= mNextShotAvailableTime;
        }

        public float cooldown_left() {
            return is_ready() ? 0 : mNextShotAvailableTime - currentTimeSec();
        }
    }
}
