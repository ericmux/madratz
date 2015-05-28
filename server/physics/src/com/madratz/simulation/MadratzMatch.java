package com.madratz.simulation;

import com.madratz.service.MatchParams;
import com.madratz.service.Player;

import java.util.List;

public class MadratzMatch {

    private final long mId;
    private final List<Player> mPlayers;

    private boolean mFinished;
    private Player mWinner;

    public MadratzMatch(long id, MatchParams params) {
        mId = id;
        mPlayers = params.getPlayers();
    }

    public void runSimulation() {
        try {
            int winnerIdx = (int) (Math.random() * mPlayers.size());
            mWinner = mPlayers.get(winnerIdx);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mFinished = true;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public double getProgress() {
        return mFinished ? 1 : 0;
    }

    public Player getWinner() {
        return mWinner;
    }

    // public List<Snapshot> getSnapshots(int start, int size);

}
