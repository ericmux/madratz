package com.madratz.behavior.api;

import com.madratz.gamelogic.player.Player;
import org.python.core.PyList;
import org.python.core.PyObject;

import java.util.HashSet;
import java.util.Set;

public class SensoringInterface {

    private final Player mPlayer;

    public SensoringInterface(Player player) {
        mPlayer = player;
    }

    public ActorInfo self() {
        return ActorInfo.fromPlayer(mPlayer);
    }

    public PyList opponents() {
        PyObject[] players = mPlayer.getWorld().getPlayers()
                .stream()
                .filter(p -> p != mPlayer)
                .map(ActorInfo::fromPlayer)
                .map(JythonWrapper::wrap)
                .toArray(PyObject[]::new);
        return new PyList(players);
    }

    public PyList particles() {
        Set<Player> players = new HashSet<>(mPlayer.getWorld().getPlayers());
        PyObject[] actors = mPlayer.getWorld().getActiveActors()
                .stream()
                .filter(p -> !players.contains(p))
                .map(ActorInfo::fromActor)
                .map(JythonWrapper::wrap)
                .toArray(PyObject[]::new);
        return new PyList(actors);
    }

    public PyList fixed_elements() {
        return new PyList(); // none for now
    }
}
