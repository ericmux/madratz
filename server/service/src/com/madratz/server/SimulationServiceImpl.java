package com.madratz.server;

import com.madratz.behavior.ScriptedBehavior;
import com.madratz.service.*;
import com.madratz.simulation.MadratzMatch;
import org.apache.thrift.TException;
import org.python.core.PyObject;
import org.python.core.PySyntaxError;
import org.python.core.PyTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.function.Function;

public class SimulationServiceImpl implements SimulationService.Iface {

    private static final Logger LOG = LoggerFactory.getLogger(SimulationServiceImpl.class);

    private final ConcurrentMap<String, MadratzMatch> mMatches = new ConcurrentHashMap<>();

    private ExecutorService mExecutor = new ThreadPoolExecutor(5, 60, 1, TimeUnit.MINUTES, new SynchronousQueue<>());

    @Override
    public void startMatch(MatchParams params) throws TException {
        MadratzMatch match = newMatch(params);

        mExecutor.submit(() -> {
            LOG.debug("Starting match " + params.getMatchId() + " simulation.");
            match.runSimulation();
            LOG.debug("Finished match " + params.getMatchId() + " simulation.");
        });
    }

    @Override
    public void finalizeMatch(String matchId) throws TException {
        getFinishedMatch(matchId);
        mMatches.remove(matchId);
    }

    @Override
    public double matchProgress(String matchId) throws TException {
        return getMatch(matchId).progress();
    }

    @Override
    public boolean isMatchFinished(String matchId) throws TException {
        return getMatch(matchId).isFinished();
    }

    @Override
    public MatchResult result(String matchId) throws TException {
        LOG.debug("Match " + matchId + " result queried.");
        MadratzMatch madratzMatch = getFinishedMatch(matchId);

        MatchResult result = new MatchResult(madratzMatch.getElapsedTime());
        PlayerInfo winner = madratzMatch.getWinner();
        if (winner != null) result.setWinnerId(winner.getId());
        return result;
    }

    @Override
    public SnapshotsResult snapshots(String matchId) throws TException {
        return getFinishedMatch(matchId).getSnapshotsResult();
    }

    @Override
    public CompilationResult compileScript(String script) throws TException {
        CompilationResult result = new CompilationResult(true);
        try {
            ScriptedBehavior behavior = new ScriptedBehavior(script);
            behavior.init();
        } catch (Exception e) {
            result.success = false;
            result.errorType = e.getClass().getSimpleName();
            result.errorMsg = compileErrorMessage(e);
        }
        return result;
    }

    private static String compileErrorMessage(Exception e) {
        try {
            PySyntaxError se = (PySyntaxError) e;
            PyObject[] value = ((PyTuple) se.value).getArray();

            String errorMsg = value[0].asString();
            PyObject[] details = ((PyTuple) value[1]).getArray();

            String line = details[3].asString().trim();
            return String.format("%d:%d '%s' Error: %s", details[1].asInt(), details[2].asInt(), line, errorMsg);
        } catch (Exception inner) {
            // if we get any error trying to create the compile error message, just return the original Exception toString
            return e.toString();
        }
    }

    private MadratzMatch getFinishedMatch(String matchId) throws TException {
        MadratzMatch match = getMatch(matchId);
        if (!match.isFinished()) {
            throw new InvalidArgumentException("Match " + matchId + " not finished yet!");
        }
        return match;
    }

    private MadratzMatch getMatch(String matchId) throws TException {
        MadratzMatch match = mMatches.get(matchId);
        if (match == null) throw new InvalidArgumentException("Match with id " + matchId + " not found");
        return match;
    }

    /**
     * Helper method for creating a MadratzMatch from a certain MatchParams with a unique ID and
     * already adding it to the mMatches map.
     */
    private MadratzMatch newMatch(MatchParams params) throws TException {
        Function<String, MadratzMatch> matchCreator = (id) -> new MadratzMatch(params);
        MadratzMatch match = mMatches.computeIfAbsent(params.getMatchId(), matchCreator);
        if (match == null) {
            throw new InvalidArgumentException("Match with id " + params.getMatchId() + " already exists.");
        }
        return match;
    }
}
