package com.madratz.cpulimit;

import com.madratz.junit.annotation.Repeat;
import com.madratz.junit.rules.RepeatRule;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TimeLimitedExecutorServiceTest {

    private static final long INTERRUPT_TIME_TOLERANCE_MS = 10L; // 10ms
    // Stopping the thread requires hitting the timeout twice, so the tolerance should be higher
    private static final long STOP_TIME_TOLERANCE_MS = 2 * INTERRUPT_TIME_TOLERANCE_MS;
    private static final long THREAD_TIME_TOLERANCE_NANOS = 100_000L; // 0.1ms

    @Rule
    public final TestRule mRepeatRule = new RepeatRule();

    private TimeLimitedExecutorService mExecutorService;

    @Before
    public void before() throws Exception {
        // Single threaded executor
        mExecutorService = new TimeLimitedExecutorService(1, 1, 0L, TimeUnit.MILLISECONDS);
    }

    @After
    public void after() throws Exception {
        mExecutorService.shutdownNow();
        mExecutorService = null;
    }

    /**
     * Check that the TimedFuture measures the time the task actually executed in thread and system time.
     */
    @Test
    @Repeat(times = 200, maxFailures = 2)
    public void testTimedFutureTimeAccurate() throws ExecutionException, InterruptedException {
        final AtomicLong actualThreadTime = new AtomicLong(0);
        long systemStartTime = System.nanoTime();

        TimedFuture<?> future = mExecutorService.submitLimited(() -> {
            long startTime = CpuTimer.currentThreadCpuTime();
            IntStream.range(1, 1000000).reduce(1, (i, j) -> i * j); // 10^6 multiplications
            actualThreadTime.set(CpuTimer.currentThreadCpuTime() - startTime);
        }, 10, TimeUnit.SECONDS);
        future.get();

        long externalSystemTime = System.nanoTime() - systemStartTime;

        long futureThreadTime = future.getThreadExecutionTime(TimeUnit.NANOSECONDS);
        assertThat(futureThreadTime, greaterThanOrEqualTo(actualThreadTime.get()));
        assertThat(futureThreadTime, lessThan(actualThreadTime.get() + THREAD_TIME_TOLERANCE_NANOS));

        long futureSystemTime = future.getSystemExecutionTime(TimeUnit.NANOSECONDS);
        assertThat(futureSystemTime, lessThanOrEqualTo(externalSystemTime));
    }

    /**
     * Test that, if no interrupt threshold is set, the executor service will default to never stopping the thread using
     * the deprecated {@link Thread#stop()} method even if the task takes much longer than the time limit requested.
     */
    @Test
    @Repeat(times = 10, maxFailures = 1)
    public void testNoStopByDefault() throws Exception {
        TimedFuture<Integer> future = mExecutorService.submitLimited(() -> {
            long startTime = CpuTimer.currentThreadCpuTime();
            while (!Thread.interrupted());
            busyWaitUntil(startTime + TimeUnit.MILLISECONDS.toNanos(100));
            return 1;
        }, 10, TimeUnit.MILLISECONDS); // Task execution will actually take 10 times the time limit

        assertEquals(1, (int) future.get());

        long threadTime = future.getThreadExecutionTime(TimeUnit.MILLISECONDS);
        assertThat(threadTime, greaterThanOrEqualTo(100L));
        assertThat(threadTime, lessThan(100L + INTERRUPT_TIME_TOLERANCE_MS));

        assertThat("Thread unexpectedly ran longer than the system itself.",
                future.getSystemExecutionTime(TimeUnit.MILLISECONDS), greaterThanOrEqualTo(threadTime));
    }

    /**
     * Check that the time the task runs before it is interrupted is actually the time we set as the limit for the task.
     */
    @Test
    @Repeat(times = 10, maxFailures = 2)
    public void testInterruptTimeConsistent() throws Exception {
        TimedFuture<?> future = mExecutorService.submitLimited(() -> {while(!Thread.interrupted());},
                100L, TimeUnit.MILLISECONDS);
        future.get();

        long threadTime = future.getThreadExecutionTime(TimeUnit.MILLISECONDS);
        assertThat(threadTime, greaterThanOrEqualTo(100L));
        assertThat(threadTime, lessThanOrEqualTo(100L + INTERRUPT_TIME_TOLERANCE_MS));
    }

    /**
     * Test that the task actually gets interrupted and is then given the "interrupt threshold" time to finish cleanly
     * before being actually stopped.
     */
    @Test
    public void testInterruptThresholdRespected() throws Exception {
        mExecutorService.setInterruptThreshold(100, TimeUnit.MILLISECONDS);

        TimedFuture<Integer> future = mExecutorService.submitLimited(() -> {
            while (!Thread.interrupted());
            busyWait(50); // 50 ms
            return 1;
        }, 100, TimeUnit.MILLISECONDS);

        assertEquals(1, (int) future.get());
    }

    /**
     * Check that the thread is actually killed if it ignores the interrupt signal for more than the interrupt threshold.
     */
    @Test
    public void testThreadKilledIfIgnoredInterrupt() throws Exception {
        mExecutorService.setInterruptThreshold(10, TimeUnit.MILLISECONDS);

        // Do it several times to make sure the killed threads are not reused (and re-killed).
        for (int i = 0; i < 5; i++) {
            AtomicBoolean interrupted = new AtomicBoolean(false);
            Future<?> future = mExecutorService.submitLimited((Runnable)() -> {
                while(!Thread.interrupted());
                interrupted.set(true);
                // The get will always be true, this is just because a "while(true)" turned some Threads into zombies.
                while(interrupted.get());
            }, 10, TimeUnit.MILLISECONDS);
            try {
                future.get();
            } catch (ExecutionException e) {
                assertThat(e.getCause(), Matchers.instanceOf(ThreadDeath.class));
                assertTrue(interrupted.get());
                continue;
            }
            fail("Expected ExecutionException caused by thread death.");
        }
    }

    /**
     * Tests if the thread is really given at least the interrupt threshold time to stop cleanly before it is actually
     * killed.
     */
    @Test(expected = ExecutionException.class)
    @Repeat(times = 10, maxFailures = 1)
    public void testThreadStoppedOnlyAfterThreshold() throws Exception {
        mExecutorService.setInterruptThreshold(40, TimeUnit.MILLISECONDS);

        TimedFuture<?> future = mExecutorService.submitLimited((Runnable)() -> {while(true);},
                10, TimeUnit.MILLISECONDS);
        try {
            future.get();
        } catch (ExecutionException e) {
            long threadTime = future.getThreadExecutionTime(TimeUnit.MILLISECONDS);
            assertThat(threadTime, greaterThanOrEqualTo(50L));
            assertThat(threadTime, lessThan(50L + STOP_TIME_TOLERANCE_MS));
            throw e;
        }
    }

    /**
     * Test the ExecutorService still works even after a task has it's thread killed due to ignoring the interrupt. This
     * is to make sure that the Thread is not reused by the ExecutorService after {@link Thread#stop()} is called on it.
     */
    @Test
    public void testStillWorkingAfterStop() throws Exception {
        mExecutorService.setInterruptThreshold(10, TimeUnit.MILLISECONDS);

        TimedFuture<?> failingFuture = mExecutorService.submitLimited((Runnable)() -> {while(true);},
                10, TimeUnit.MILLISECONDS);
        Throwable thrown = null;
        try {
            failingFuture.get();
        } catch (ExecutionException e) {
            thrown = e;
        }
        assertNotNull(thrown);

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            assertEquals(i, (int) mExecutorService.submit(() -> finalI).get());
        }
    }

    private void busyWait(long delayMillis) {
        busyWaitUntil(CpuTimer.currentThreadCpuTime() + TimeUnit.MILLISECONDS.toNanos(delayMillis));
    }

    private void busyWaitUntil(long timeoutNanos) {
        while (CpuTimer.currentThreadCpuTime() < timeoutNanos);
    }
}
