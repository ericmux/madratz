package com.madratz.cpulimit;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeLimitedExecutorService extends ThreadPoolExecutor {

    /*test*/ static final long PRONE_PRECISION_NANOS = 1_000_000; // 1ms
    private static final AtomicInteger sExecutorCount = new AtomicInteger(0);

    private Thread mPronerThread = new Thread(new TasksProner(), "time-limited-proner-" + sExecutorCount.incrementAndGet());

    private DelayQueue<TimeLimitedTask> mRunningTasks = new DelayQueue<>();
    private long mInterruptThresholdNanos = -1;

    public TimeLimitedExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>());
    }

    public TimeLimitedExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TimeLimitedExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public TimeLimitedExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public TimeLimitedExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public long getInterruptThreshold(TimeUnit unit) {
        return unit.convert(mInterruptThresholdNanos, TimeUnit.NANOSECONDS);
    }

    public void setInterruptThreshold(long tolerance, TimeUnit unit) {
        mInterruptThresholdNanos = unit.toNanos(tolerance);
    }

    public <T> TimedFuture<T> submitLimited(Callable<T> method, long timeLimit, TimeUnit unit) {
        if (!mPronerThread.isAlive()) mPronerThread.start();

        final TimedFutureImpl<T> timedFuture = new TimedFutureImpl<>();
        timedFuture.setInnerFuture(submit(() -> {
            long nanosDelay = unit.toNanos(timeLimit);
            TimeLimitedTask task = new TimeLimitedTask(Thread.currentThread(),
                    CpuTimer.currentThreadCpuTime() + nanosDelay,
                    systemTimeout(nanosDelay));
            mRunningTasks.offer(task);

            try {
                long systemStartTime = System.nanoTime();
                long threadStartTime = CpuTimer.currentThreadCpuTime();
                try {
                    return method.call();
                } finally {
                    timedFuture.setThreadExecutionTimeNanos(CpuTimer.currentThreadCpuTime() - threadStartTime);
                    timedFuture.setSystemExecutionTimeNanos(System.nanoTime() - systemStartTime);
                }
            } catch (Throwable t) {
                if ((t instanceof InterruptedException || t.getCause() instanceof InterruptedException)) {
                    if (!mRunningTasks.contains(task)) {
                        throw new TimeoutException();
                    }
                }
                throw t;
            } finally {
                mRunningTasks.remove(task);
            }
        }));

        return timedFuture;
    }

    public TimedFuture<?> submitLimited(Runnable command, long timeLimit, TimeUnit unit) {
        return submitLimited(() -> {
            command.run();
            return null;
        }, timeLimit, unit);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        if (t == null && r instanceof Future<?>) {
            try {
                ((Future<?>) r).get();
            } catch (ExecutionException e) {
                t = e.getCause();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                t = e;
            }
        }

        // If the thread has been stopped make sure we rethrow the thread death
        if (t instanceof ThreadDeath) {
            throw (ThreadDeath)t;
        }
    }

    @Override
    protected void terminated() {
        super.terminated();
        mPronerThread.interrupt();
    }

    static long systemTimeout(long threadNanoDelay) {
        return System.nanoTime() + threadNanoDelay + PRONE_PRECISION_NANOS;
    }

    private class TimedFutureImpl<T> extends DelegatedFuture<T> implements TimedFuture<T> {
        private long mThreadExecutionTimeNanos;
        private long mSystemExecutionTimeNanos;

        TimedFutureImpl() {
            super(null);
        }

        void setInnerFuture(Future<T> future) {
            if (mInnerFuture != null) throw new IllegalStateException("Inner future may be set only once");
            mInnerFuture = future;
        }

        @Override
        public long getThreadExecutionTime(TimeUnit unit) {
            if (!mInnerFuture.isDone()) return -1;
            return unit.convert(mThreadExecutionTimeNanos, TimeUnit.NANOSECONDS);
        }

        @Override
        public long getSystemExecutionTime(TimeUnit unit) {
            if (!mInnerFuture.isDone()) return -1;
            return unit.convert(mSystemExecutionTimeNanos, TimeUnit.NANOSECONDS);
        }

        public void setThreadExecutionTimeNanos(long threadExecutionTimeNanos) {
            mThreadExecutionTimeNanos = threadExecutionTimeNanos;
        }

        public void setSystemExecutionTimeNanos(long systemExecutionTimeNanos) {
            mSystemExecutionTimeNanos = systemExecutionTimeNanos;
        }
    }

    private class TasksProner implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeLimitedTask candidate = mRunningTasks.take();
                    long threadCpuTime = CpuTimer.threadCpuTime(candidate.thread);

                    if (candidate.threadTimeout > threadCpuTime) {
                        long delay = candidate.threadTimeout - threadCpuTime;
                        candidate.systemTimeout = systemTimeout(delay);
                        mRunningTasks.offer(candidate);
                    } else {
                        // Timeout expired!
                        if (!candidate.interrupted) {
                            candidate.thread.interrupt();

                            if (mInterruptThresholdNanos >= 0) {
                                candidate.interrupted = true;
                                candidate.threadTimeout = threadCpuTime + mInterruptThresholdNanos;
                                candidate.systemTimeout = systemTimeout(mInterruptThresholdNanos);
                                mRunningTasks.offer(candidate);
                            }
                        } else {
                            // Thread already interrupted and tolerance time exceeded. Stop the thread ruthlessly.
                            candidate.thread.stop();
                        }
                    }
                } catch (InterruptedException e) {
                    // If this thread is interrupted it means we are shutting down.
                    return;
                }
            }
        }
    }

    private final class TimeLimitedTask implements Delayed {
        public final Thread thread;
        public long threadTimeout;
        public long systemTimeout;
        public boolean interrupted;

        private TimeLimitedTask(Thread thread, long threadTimeout, long systemTimeout) {
            this(thread, threadTimeout, systemTimeout, false);
        }

        private TimeLimitedTask(Thread thread, long threadTimeout, long systemTimeout, boolean interrupted) {
            this.thread = thread;
            this.threadTimeout = threadTimeout;
            this.systemTimeout = systemTimeout;
            this.interrupted = interrupted;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(systemTimeout - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o instanceof TimeLimitedTask) return Long.compare(systemTimeout, ((TimeLimitedTask)o).systemTimeout);
            return Long.compare(getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
        }
    }
}
