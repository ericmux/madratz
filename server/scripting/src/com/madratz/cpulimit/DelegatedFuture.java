package com.madratz.cpulimit;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DelegatedFuture<T> implements Future<T> {

    protected Future<T> mInnerFuture;

    public DelegatedFuture(Future<T> innerFuture) {
        mInnerFuture = innerFuture;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return mInnerFuture.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return mInnerFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return mInnerFuture.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return mInnerFuture.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return mInnerFuture.get(timeout, unit);
    }
}
