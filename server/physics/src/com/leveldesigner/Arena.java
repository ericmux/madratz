package com.leveldesigner;

import com.behaviors.ShootBehavior;
import com.gamelogic.Actor;
import com.simulation.MadratzWorld;
import org.jbox2d.common.Vec2;

/**
 * This class will (ideally) parse XML/JSON/Protobuff files and build the corresponding MadratzWorld instance from it.
 * Its current implementation will just build a hardcoded level for now.
 */
public class Arena {

    private int mNumberOfPlayers;
    private MadratzBuilder mMadratzBuilder;

    public Arena(int numberOfPlayers) {
        mNumberOfPlayers = numberOfPlayers;
        mMadratzBuilder = new MadratzBuilder();
    }

    public MadratzWorld buildWorld(){

        double theta = Math.PI/3;
        float L = 40.0f;

        mMadratzBuilder = new MadratzBuilder(110.f,90.0f);

        for(int i = 0; i < mNumberOfPlayers; i++){
            Vec2 p = new Vec2((float)-Math.cos(theta*i),(float)Math.sin(theta*i)).mulLocal(L);
            float angle = -(float)theta*i;

            mMadratzBuilder.addActor(new Actor(new ShootBehavior(), p, angle));
        }

        mMadratzBuilder.addWalls().setGravity(new Vec2());

        return mMadratzBuilder.build();


    }
}
