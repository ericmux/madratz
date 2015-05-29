package com.madratz.server;

import com.madratz.service.MatchParams;
import com.madratz.service.MatchResult;
import com.madratz.service.PlayerInfo;
import com.madratz.service.SimulationService;
import com.madratz.simulation.MadratzMatch;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class SimulationServiceImpl implements SimulationService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(SimulationServiceImpl.class);

    private final AtomicLong mNextMatchId = new AtomicLong(1);
    private final Map<Long, MadratzMatch> mMatches = new ConcurrentHashMap<>();

    private ExecutorService mExecutor = new ThreadPoolExecutor(5, 60, 1, TimeUnit.MINUTES, new SynchronousQueue<>());

    @Override
    public long startMatch(MatchParams params) throws TException {
        long matchId = mNextMatchId.getAndIncrement();
        MadratzMatch match = new MadratzMatch(matchId, params);
        mMatches.put(matchId, match);

        mExecutor.submit(() -> {
            LOG.debug("Starting match %d simulation.", matchId);
            match.runSimulation();
            LOG.debug("Finished match %d simulation.", matchId);
        });

        return matchId;
    }

    @Override
    public boolean isMatchFinished(long matchId) throws TException {
        return getMatch(matchId).isFinished();
    }

    @Override
    public MatchResult result(long matchId) throws TException {
        if (!isMatchFinished(matchId)) {
            throw new TApplicationException("Match not finished yet!");
        }
        LOG.debug("Match %d result queried.", matchId);
        MadratzMatch madratzMatch = getMatch(matchId);

        MatchResult result = new MatchResult(madratzMatch.getElapsedTime());
        PlayerInfo winner = madratzMatch.getWinner();
        if (winner != null) result.setWinnerId(winner.getId());
        return result;
    }

    private MadratzMatch getMatch(long matchId) throws TApplicationException {
        MadratzMatch match = mMatches.get(matchId);
        if (match == null) throw new TApplicationException("Match with id " + matchId + " not found");
        return match;
    }
}
