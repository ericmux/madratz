package com.madratz.gamelogic;

import com.madratz.decision.Decision;
import com.madratz.decision.MoveRequest;
import com.madratz.gamelogic.player.Player;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Projectile extends Actor {

    private final float mHitDamage;

    public Projectile(Vec2 position, float angle, float speed, float radius, float hitDamage) {
        super(a -> new Decision(MoveRequest.forVelocity(a, speed)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        bodyDef.allowSleep = false;
        bodyDef.bullet = true;
        bodyDef.angle = angle;
        bodyDef.fixedRotation = true;


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5.0f;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = 0x0002;
        fixtureDef.filter.maskBits = 0xFFFD;

        mWidth = 2*fixtureDef.shape.getRadius();

        mBodyDef = bodyDef;
        mFixtureDef = fixtureDef;
        mBody = null;
        mHitDamage = hitDamage;
    }

    @Override
    public void handleCollision(Actor actor) {
        if (actor instanceof Player) {
            Player player = ((Player) actor);
            player.inflictDamage(mHitDamage);
        }
        getWorld().destroyActor(this);
    }
}
