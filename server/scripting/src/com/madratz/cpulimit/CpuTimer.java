package com.madratz.cpulimit;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CpuTimer {

    private static final ThreadMXBean THREAD_MX = ManagementFactory.getThreadMXBean();

    static {
        if (THREAD_MX.isThreadCpuTimeSupported()) {
            THREAD_MX.setThreadCpuTimeEnabled(true);
        }
    }

    /**
     * Whether the JVM supports Thread CPU time. Depending on this the CPU time functions might just return the system
     * nano time.
     */
    public static boolean isThreadCpuTimeSupported() {
        return THREAD_MX.isThreadCpuTimeSupported();
    }

    /**
     * CPU time for the current (calling) thread. See {@link CpuTimer#threadCpuTime(long)}.
     */
    public static long currentThreadCpuTime() {
        return threadCpuTime(Thread.currentThread().getId());
    }

    /**
     * CPU time for the specified Thread object. See {@link CpuTimer#threadCpuTime(long)}.
     */
    public static long threadCpuTime(Thread thread) {
        return threadCpuTime(thread.getId());
    }

    /**
     * Returns the CPU time in nanoseconds for the Thread if the specified ID. See {@link ThreadMXBean#getThreadCpuTime(long)}
     * for more information.
     *
     * If Thread CPU time is not supported in the current JVM, the system nano ({@link System#nanoTime()}) time is
     * returned instead.
     */
    public static long threadCpuTime(long threadId) {
        long time = THREAD_MX.getThreadCpuTime(threadId);
        if (time < 0) return System.nanoTime();
        return time;
    }
}
