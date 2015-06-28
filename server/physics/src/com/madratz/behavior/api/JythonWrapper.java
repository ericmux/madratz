package com.madratz.behavior.api;

import com.madratz.security.Privileged;
import org.python.core.PyJavaType;
import org.python.core.PyObject;

// TODO: Make actual Python classes for these wrappers
public final class JythonWrapper {

    public static PyObject wrap(Object obj) {
        try {
            return Privileged.callPrivileged(() -> PyJavaType.wrapJavaObject(obj));
        } catch (Exception e) {
            // this should not happen, wrap in RuntimeException anyway just for the compiler
            throw new RuntimeException(e);
        }
    }

    // uninstantiable
    private JythonWrapper() {}
}
