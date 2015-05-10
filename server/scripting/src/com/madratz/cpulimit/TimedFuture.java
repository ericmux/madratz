package com.madratz.cpulimit;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface TimedFuture<T> extends Future<T> {
    public long getThreadExecutionTime(TimeUnit unit);

    public long getSystemExecutionTime(TimeUnit unit);
}
