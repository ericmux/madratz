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
        Vec2 spellPos = mActor.getBody().getWorldPoint(new Vec2(mActor.getWidth(),0.0f));

        Spell spell = new Spell(spellPos,Spell.MAX_SPELL_SPEED,0.8f,0.1f,20.0f);

        mActor.getWorld().registerActor(spell);
        spell.getBody().setLinearVelocity(mActor.getBody().getLinearVelocity().clone().mulLocal(3.0f));
    }
}
