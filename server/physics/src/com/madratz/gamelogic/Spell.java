package com.madratz.gamelogic;

import com.madratz.decision.Decision;
import com.madratz.decision.MoveRequest;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Spell extends Actor {

    private static final float MAX_SPELL_SPEED = 3 * Player.MAX_LINEAR_SPEED;

    private final float mHitDamage;

    public Spell(Vec2 position, float angle, float speed, float radius, float hitDamage) {
        super(a -> new Decision(MoveRequest.forVelocity(a, MAX_SPELL_SPEED * speed)));
        assert speed > 0 && speed <= 1;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        bodyDef.allowSleep = false;
        bodyDef.angle = angle;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5.0f;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;

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
