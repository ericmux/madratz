package com.madratz.simulation;

import com.madratz.gamelogic.CollisionHandler;
import com.madratz.gamelogic.player.Player;
import com.madratz.leveldesigner.WorldLoader;
import com.madratz.serialization.Snapshot;
import com.madratz.service.MatchParams;
import com.madratz.service.PlayerInfo;
import com.madratz.service.SnapshotsResult;
import com.madratz.ui.SimulationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MadratzMatch {

    private static final Logger LOG = LoggerFactory.getLogger(MadratzMatch.class);

    private static float EXTRA_SIMULATED_TIME_SEC = 2;

    private final float mTimeLimitSec;
    private final List<PlayerInfo> mPlayers;

    private boolean mFinished;
    private PlayerInfo mWinner;

    private MadratzWorld mWorld;
    private List<Snapshot> mSnapshots = new LinkedList<>();

    public MadratzMatch(MatchParams params) {
        mTimeLimitSec = params.getTimeLimitSec();
        mPlayers = params.getPlayers();
        mFinished = false;

        if(mPlayers.size() < 2) throw new IllegalArgumentException("Too few players: " + mPlayers.size());

        mWorld = WorldLoader.buildScriptedWorldFor(mPlayers);
        mWorld.setContactListener(new CollisionHandler());
    }

    public void runSimulation() {
        mSnapshots.add(mWorld.toThrift());

        float timeLimit = mTimeLimitSec;
        int lastPlayerCount = mWorld.getPlayers().size();
        while (mWorld.getElapsedTime() < timeLimit) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Stepping simulation... " + mWorld.getElapsedTime() + "s elapsed. Players alive: " + mWorld.getPlayers().size());
            }
            synchronized (this) {
                mWorld.step(SimulationTest.TIMESTEP, SimulationTest.VEL_ITERATIONS, SimulationTest.POS_ITERATIONS);
            }
            mSnapshots.add(mWorld.toThrift());

            int playerCount = mWorld.getPlayers().size();
            if (playerCount < lastPlayerCount && playerCount <= 1) {
                timeLimit = mWorld.getElapsedTime() + EXTRA_SIMULATED_TIME_SEC;
            }
            lastPlayerCount = playerCount;
        }

        Player winner = findWinner(mWorld.getPlayers()).orElse(null);
        if (winner != null) {
            mWinner = mPlayers.stream()
                    .filter(p -> p.id.equals(winner.getId()))
                    .findFirst()
                    .get();
        }
        mFinished = true;
    }

    public double progress() {
        if (isFinished()) return 1;
        synchronized (this) {
            double timePercentage = mWorld.getElapsedTime() / mTimeLimitSec;
            // Sum the hp percentage from the players alive and divide by the number of initial players
            double hpPercentage = mWorld.getPlayers().stream().mapToDouble(p -> p.getHP() / p.maxHP()).sum() / mPlayers.size();
            // Return the "percentage" closest to completing
            return Math.min(1, Math.max(timePercentage, 1 - hpPercentage));
        }
    }

    public boolean isFinished() {
        return mFinished;
    }

    public PlayerInfo getWinner() {
        return mWinner;
    }

    public float getElapsedTime(){ return mWorld.getElapsedTime(); }

    public SnapshotsResult getSnapshotsResult() {
        assert mFinished;
        return new SnapshotsResult(mSnapshots);
    }

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
