package com.madratz.leveldesigner;

import com.madratz.behavior.Behavior;
import com.madratz.behavior.ScriptedBehavior;
import com.madratz.behavior.ShootBehavior;
import com.madratz.gamelogic.Player;
import com.madratz.jython.ScriptLoader;
import com.madratz.service.PlayerInfo;
import com.madratz.simulation.MadratzWorld;
import org.jbox2d.common.Vec2;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * This class will (ideally) parse XML/JSON/Protobuff files and build the corresponding MadratzWorld instance from it.
 * Its current implementation will just build a hardcoded level for now.
 */
public class WorldLoader {

    public static MadratzWorld buildWorld(int numberOfPlayers) {
        List<Behavior> behaviors = Stream.generate(ShootBehavior::new)
                .limit(numberOfPlayers)
                .collect(toList());
        return buildRegularWorld(behaviors, null);
    }


    public static MadratzWorld buildScriptedWorld(int numberOfPlayers){
        List<Behavior> behaviors = Stream.generate(() -> ScriptLoader.readScript("circle_behavior.py"))
                .limit(numberOfPlayers)
                .map(ScriptedBehavior::new)
                .collect(toList());

        return buildRegularWorld(behaviors, null);
    }

    public static MadratzWorld buildScriptedWorldFor(List<PlayerInfo> players) {
        List<Behavior> behaviors = players.stream()
                .map(PlayerInfo::getScript)
                .map(ScriptedBehavior::new)
                .collect(toList());
        return buildRegularWorld(behaviors, players.stream().map(PlayerInfo::getId).collect(toList()));
    }

    private static MadratzWorld buildRegularWorld(List<Behavior> behaviors, List<Long> ids) {
        int numPlayers = behaviors.size();
        double theta = 2 * Math.PI / numPlayers;
        float L = 40.0f;

        MadratzBuilder madratzBuilder = new MadratzBuilder(100.0f,100.0f);

        for(int i = 0; i < behaviors.size(); i++) {
            Vec2 p = new Vec2((float)-Math.cos(theta * i), (float)Math.sin(theta * i)).mulLocal(L);
            float angle = -(float)theta * i;
            long id = (ids == null ? i : ids.get(i));
            madratzBuilder.addActor(new Player(id, behaviors.get(i), p, angle));
        }

        madratzBuilder.addWalls().setGravity(new Vec2());

        return madratzBuilder.build();
    }
}
