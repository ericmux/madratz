package com.madratz.simulation;

import com.madratz.gamelogic.CollisionHandler;
import com.madratz.gamelogic.Player;
import com.madratz.leveldesigner.WorldLoader;
import com.madratz.serialization.Snapshot;
import com.madratz.service.MatchParams;
import com.madratz.service.PlayerInfo;
import com.madratz.ui.SimulationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MadratzMatch {

    private static final Logger LOG = LoggerFactory.getLogger(MadratzMatch.class);

    private final long mId;
    private final float mTimeLimitSec;
    private final List<PlayerInfo> mPlayers;

    private boolean mFinished;
    private PlayerInfo mWinner;

    private MadratzWorld mWorld;
    private List<Snapshot> mSnapshots = new LinkedList<>();

    public MadratzMatch(long id, MatchParams params) {
        mId = id;
        mTimeLimitSec = params.getTimeLimitSec();
        mPlayers = params.getPlayers();
        mFinished = false;

        if(mPlayers.size() < 2) throw new IllegalArgumentException("Too few players: " + mPlayers.size());

        mWorld = WorldLoader.buildScriptedWorldFor(mPlayers);
        mWorld.setContactListener(new CollisionHandler());
    }

    public long getId() {
        return mId;
    }

    public void runSimulation() {
        mSnapshots.add(mWorld.toThrift());

        while (mWorld.getPlayers().size() >= 2 && mWorld.getElapsedTime() < mTimeLimitSec) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Stepping simulation... " + mWorld.getElapsedTime() + "s elapsed. Players alive: " + mWorld.getPlayers().size());
            }
            mWorld.step(SimulationTest.TIMESTEP, SimulationTest.VEL_ITERATIONS, SimulationTest.POS_ITERATIONS);
            mSnapshots.add(mWorld.toThrift());
        }

        Player winner = findWinner(mWorld.getPlayers()).orElse(null);
        if (winner != null) {
            mWinner = mPlayers.stream()
                    .filter(p -> p.id == winner.getId())
                    .findFirst()
                    .get();
        }
        mFinished = true;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public PlayerInfo getWinner() {
        return mWinner;
    }

    public float getElapsedTime(){ return mWorld.getElapsedTime(); }

    public List<Snapshot> getSnapshots() {
        assert mFinished;
        return mSnapshots;
    }

    // public double getProgress();

    private static Optional<Player> findWinner(List<Player> standingPlayers) {
        if (standingPlayers.size() == 1) {
            return Optional.of(standingPlayers.get(0));
        } else if (standingPlayers.size() > 1) {
            standingPlayers.sort(Comparator.comparing(Player::getHP).reversed());
            Player highest = standingPlayers.get(0), second = standingPlayers.get(1);
            if (highest.getHP() == second.getHP()) return Optional.empty(); // Same HP, no one wins
            return Optional.of(highest);
        } else {
            return Optional.empty();
        }
    }
}
