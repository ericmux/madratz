package com.madratz.simulation;

import com.madratz.gamelogic.CollisionHandler;
import com.madratz.gamelogic.Player;
import com.madratz.leveldesigner.WorldLoader;
import com.madratz.service.MatchParams;
import com.madratz.service.PlayerInfo;
import com.madratz.ui.SimulationTest;

import java.util.List;

public class MadratzMatch {

    private static final float DEFAULT_MAX_TIME = 300.0f;

    private final float mMaxTime;
    private final long mId;
    private final List<PlayerInfo> mPlayers;

    private boolean mFinished;
    private PlayerInfo mWinner;

    private MadratzWorld mWorld;

    public MadratzMatch(long id, MatchParams params) {
        mId = id;
        mMaxTime = DEFAULT_MAX_TIME;
        mPlayers = params.getPlayers();
        mFinished = false;

        if(mPlayers.size() < 2) throw new IllegalArgumentException("Too few players: " + mPlayers.size());

        mWorld = WorldLoader.buildScriptedWorldFor(mPlayers);
        mWorld.setContactListener(new CollisionHandler());
    }

    public void runSimulation() {

        while(!mFinished){

            mWorld.step(SimulationTest.TIMESTEP,SimulationTest.VEL_ITERATIONS,SimulationTest.POS_ITERATIONS);

            System.out.println(mWorld.getPlayers().size());

            if(mWorld.getPlayers().size() < 2) mFinished = true;
        }
        int winnerId = ((Player) mWorld.getPlayers().get(0)).getId();
        mWinner = mPlayers.get(winnerId);

    }

    public boolean isFinished() {
        return mFinished;
    }

    public double getProgress() {
        return mFinished ? 1 : 0;
    }

    public PlayerInfo getWinner() {
        return mWinner;
    }

    public float getElapsedTime(){ return mWorld.getElapsedTime(); }

    // public List<Snapshot> getSnapshots(int start, int size);

}
