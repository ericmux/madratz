package com.madratz.server;

import com.madratz.serialization.Snapshot;
import com.madratz.service.*;
import com.madratz.simulation.MadratzMatch;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class SimulationServiceImpl implements SimulationService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(SimulationServiceImpl.class);

    private final AtomicLong mNextMatchId = new AtomicLong(1);
    private final ConcurrentMap<Long, MadratzMatch> mMatches = new ConcurrentHashMap<>();

    private ExecutorService mExecutor = new ThreadPoolExecutor(5, 60, 1, TimeUnit.MINUTES, new SynchronousQueue<>());

    @Override
    public long startMatch(MatchParams params) throws TException {
        MadratzMatch match = newMatch(params);
        long matchId = match.getId();

        mExecutor.submit(() -> {
            LOG.debug("Starting match " + matchId + " simulation.");
            match.runSimulation();
            LOG.debug("Finished match " + matchId + " simulation.");
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
            throw new InvalidArgumentException("Match not finished yet!");
        }
        LOG.debug("Match " + matchId + " result queried.");
        MadratzMatch madratzMatch = getMatch(matchId);

        MatchResult result = new MatchResult(madratzMatch.getElapsedTime());
        PlayerInfo winner = madratzMatch.getWinner();
        if (winner != null) result.setWinnerId(winner.getId());
        return result;
    }

    private MadratzMatch getMatch(long matchId) throws TException {
        MadratzMatch match = mMatches.get(matchId);
        if (match == null) throw new InvalidArgumentException("Match with id " + matchId + " not found");
        return match;
    }

    /**
     * Helper method for creating a MadratzMatch from a certain MatchParams with a unique ID and
     * already adding it to the mMatches map.
     */
    private MadratzMatch newMatch(MatchParams params) throws TException {
        Function<Long, MadratzMatch> matchCreator = (id) -> new MadratzMatch(id, params);
        MadratzMatch match = null;
        if (params.isSetMatchId()) {
            match = mMatches.computeIfAbsent(params.getMatchId(), matchCreator);
            if (match == null) {
                throw new InvalidArgumentException("Match with id " + params.getMatchId() + " already existent.");
            }
        } else {
            while (match == null) {
                match = mMatches.computeIfAbsent(mNextMatchId.getAndIncrement(), matchCreator);
            }
        }
        return match;
    }
}
