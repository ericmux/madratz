package com.madratz.security;

import java.util.concurrent.Callable;

/**
 * Helper class to use when you want to call some code you trust but that is in a
 * protection domain you would like to be sandboxed at other times. When the call
 * goes through this class, only the permissions that were present at the time of
 * calling this class will be considered.
 * i.e. There is no way of "getting out" of a protected domain once you are inside
 * one, only of executing some specific code that would be normally unsafe without
 * restricting it by its own protection domains. This is unlike Java's built-in
 * {@link java.security.AccessController#doPrivileged}.
 * e.g. Initializing the PythonInterpreter requires some additional permissions and
 * you know it's safe code to execute, though it's still under the org.python domain
 * which is where unsafe users' scripts will execute.
 */
public final class Privileged {
    private Privileged() {}

    public static void doPrivileged(Runnable cmd) {
        cmd.run();
    }

    public static <T> T callPrivileged(Callable<T> method) throws Exception {
        return method.call();
    }
}
