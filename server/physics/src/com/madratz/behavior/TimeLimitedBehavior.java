package com.madratz.behavior;

import com.madratz.cpulimit.TimeLimitedExecutorService;
import com.madratz.cpulimit.TimedFuture;
import com.madratz.decision.Decision;
import com.madratz.gamelogic.Actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeLimitedBehavior implements Behavior {

    private static final Logger LOG = LoggerFactory.getLogger(TimeLimitedBehavior.class);

    private final Behavior mInnerBehavior;

    private final TimeLimitedExecutorService mExecutorService;

    private long mExecutionTime;
    private final long mMaxExecutionTime;
    private final long mTimePerStep;
    private final TimeUnit mUnit;

    public TimeLimitedBehavior(Behavior innerBehavior,
                               TimeLimitedExecutorService executor,
                               long initialExecTime,
                               long timePerStep,
                               long maxAccumulatedTime,
                               TimeUnit unit) {
        mExecutorService = executor;
        mInnerBehavior = innerBehavior;

        mExecutionTime = initialExecTime;
        mTimePerStep = timePerStep;
        mMaxExecutionTime = maxAccumulatedTime;
        mUnit = unit;
    }

    @Override
    public Decision execute(Actor actor) throws Exception {
        TimedFuture<Decision> future = mExecutorService.submitLimited(() -> mInnerBehavior.execute(actor), mExecutionTime, mUnit);
        try {
            return future.get();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof TimeoutException || e.getCause() instanceof ThreadDeath) {
                LOG.warn(actor + " timed out after " + future.getThreadExecutionTime(TimeUnit.MILLISECONDS) + "ms! " +
                        "(" + future.getSystemExecutionTime(TimeUnit.MILLISECONDS) + "ms system time)");
            } else {
                Throwable cause = e.getCause();
                if (cause instanceof Exception) {
                    throw (Exception)cause;
                } else if (cause instanceof Error) {
                    throw (Error)cause;
                } else {
                    LOG.error("Unknown Throwable implementation: " + cause, cause);
                }
            }
        } finally {
            long execTime = future.getThreadExecutionTime(mUnit);
            updateExecutionTime(execTime);
        }
        return new Decision();
    }

    private void updateExecutionTime(long executedTime) {
        long nextTime = mExecutionTime - executedTime + mTimePerStep;
        mExecutionTime = Math.min(Math.max(nextTime, mTimePerStep), mMaxExecutionTime);
    }
}
