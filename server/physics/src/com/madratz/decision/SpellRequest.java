package com.madratz.decision;

import com.madratz.gamelogic.Actor;
import com.madratz.gamelogic.Spell;
import org.jbox2d.common.Vec2;

public class SpellRequest extends ActionRequest {

    public SpellRequest(Actor actor) {
        super(actor);
    }

    @Override
    public void handle() {
        Vec2 actorFront = mActor.getBody().getWorldPoint(new Vec2(mActor.getWidth(), 0.0f));
        float angle = mActor.getBody().getAngle();
        Spell spell = new Spell(actorFront, angle, 1, 0.1f, 20);

        mActor.getWorld().registerActor(spell);
    }
}
