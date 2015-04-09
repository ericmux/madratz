package com.decision;

import com.gamelogic.Actor;
import com.gamelogic.Spell;
import com.simulation.MadratzWorld;
import org.jbox2d.common.Vec2;

public class SpellRequest implements ActionRequest {

    private Actor mActor;
    private MadratzWorld mMadratzWorld;

    public SpellRequest(Actor actor, MadratzWorld madratzWorld) {
        mActor = actor;
        mMadratzWorld = madratzWorld;
    }

    @Override
    public void handle() {

        Vec2 spellPos = mActor.getBody().getWorldPoint(new Vec2(mActor.getWidth(),0.0f));

        Spell spell = new Spell(spellPos,Spell.MAX_SPELL_SPEED,0.5f,0.1f);

    }
}
