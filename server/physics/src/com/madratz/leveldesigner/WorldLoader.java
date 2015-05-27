package com.madratz.leveldesigner;

import com.madratz.behavior.ScriptedBehavior;
import com.madratz.behavior.ShootBehavior;
import com.madratz.gamelogic.Player;
import com.madratz.jython.ScriptHandler;
import com.madratz.service.PlayerInfo;
import com.madratz.simulation.MadratzWorld;
import org.jbox2d.common.Vec2;

import java.util.List;

/**
 * This class will (ideally) parse XML/JSON/Protobuff files and build the corresponding MadratzWorld instance from it.
 * Its current implementation will just build a hardcoded level for now.
 */
public class WorldLoader {

    public static MadratzWorld buildWorld(int numberOfPlayers) {

        double theta = Math.PI/3;
        float L = 40.0f;

        MadratzBuilder madratzBuilder = new MadratzBuilder(110.f,90.0f);

        for(int i = 0; i < numberOfPlayers; i++){
            Vec2 p = new Vec2((float)-Math.cos(theta*i),(float)Math.sin(theta*i)).mulLocal(L);
            float angle = -(float)theta*i;

            madratzBuilder.addActor(new Player(i,new ShootBehavior(), p, angle));
        }

        madratzBuilder.addWalls().setGravity(new Vec2());

        return madratzBuilder.build();
    }


    public static MadratzWorld buildScriptedWorld(int numberOfPlayers){

        double theta = Math.PI/3;
        float L = 40.0f;

        MadratzBuilder madratzBuilder = new MadratzBuilder(110.0f,90.0f);
        String script = ScriptHandler.readScript("circle_behavior.py");

        for(int i = 0; i < numberOfPlayers; i++){
            Vec2 p = new Vec2((float)-Math.cos(theta*i),(float)Math.sin(theta*i)).mulLocal(L);
            float angle = -(float)theta*i;

            madratzBuilder.addActor(new Player(i,new ScriptedBehavior(script), p, angle));
        }

        madratzBuilder.addWalls().setGravity(new Vec2());

        return madratzBuilder.build();

    }

    public static MadratzWorld buildScriptedWorldFor(List<PlayerInfo> players){
        double theta = Math.PI/3;
        float L = 40.0f;

        MadratzBuilder madratzBuilder = new MadratzBuilder(110.0f,90.0f);

        for(int i = 0; i < players.size(); i++){
            Vec2 p = new Vec2((float)-Math.cos(theta*i),(float)Math.sin(theta*i)).mulLocal(L);
            float angle = -(float)theta*i;

            madratzBuilder.addActor(new Player(i,new ScriptedBehavior(players.get(i).getScript()), p, angle));
        }

        madratzBuilder.addWalls().setGravity(new Vec2());

        return madratzBuilder.build();
    }
}
