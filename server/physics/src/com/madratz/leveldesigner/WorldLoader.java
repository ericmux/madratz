package com.madratz.leveldesigner;

import com.madratz.behavior.ScriptedBehavior;
import com.madratz.behavior.ShootBehavior;
import com.madratz.gamelogic.Actor;
import com.madratz.simulation.MadratzWorld;
import org.jbox2d.common.Vec2;

/**
 * This class will (ideally) parse XML/JSON/Protobuff files and build the corresponding MadratzWorld instance from it.
 * Its current implementation will just build a hardcoded level for now.
 */
public class WorldLoader {

    public static MadratzWorld buildWorld(int numberOfPlayers, long matchID) {

        double theta = Math.PI/3;
        float L = 40.0f;

        MadratzBuilder madratzBuilder = new MadratzBuilder(110.f,90.0f);
        madratzBuilder.setMatchID(matchID);

        for(int i = 0; i < numberOfPlayers; i++){
            Vec2 p = new Vec2((float)-Math.cos(theta*i),(float)Math.sin(theta*i)).mulLocal(L);
            float angle = -(float)theta*i;

            madratzBuilder.addActor(new Actor(new ShootBehavior(), p, angle));
        }

        madratzBuilder.addWalls().setGravity(new Vec2());

        return madratzBuilder.build();
    }


    public static MadratzWorld buildScriptedWorld(int numberOfPlayers, long matchID){

        double theta = Math.PI/3;
        float L = 40.0f;

        MadratzBuilder madratzBuilder = new MadratzBuilder(110.0f,90.0f);
        madratzBuilder.setMatchID(matchID);

        for(int i = 0; i < numberOfPlayers; i++){
            Vec2 p = new Vec2((float)-Math.cos(theta*i),(float)Math.sin(theta*i)).mulLocal(L);
            float angle = -(float)theta*i;

            madratzBuilder.addActor(new Actor(new ScriptedBehavior("circle_behavior.py"), p, angle));
        }

        madratzBuilder.addWalls().setGravity(new Vec2());

        return madratzBuilder.build();

    }
}
