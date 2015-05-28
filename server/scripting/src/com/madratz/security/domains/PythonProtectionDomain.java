package com.madratz.security.domains;

import com.madratz.security.permissions.SimplePermissionCollection;

import java.io.FilePermission;
import java.lang.reflect.ReflectPermission;
import java.security.PermissionCollection;
import java.util.PropertyPermission;

public class PythonProtectionDomain extends SimpleProtectionDomain {

    static final String[] PYTHON_PKG = {"org.python"};
    static final String[] EXCLUDED_PKGS = {
            "org.python.core.PyReflectedConstructor",
            "org.python.core.PyReflectedField",
            "org.python.core.PyReflectedFunction",
            "org.python.core.BytecodeLoader"
    };

    static final PermissionCollection ALLOWED_PERMISSIONS = SimplePermissionCollection.builder()
            .add(new FilePermission("-", "read"))
            .add(new ReflectPermission("suppressAccessChecks"))
            .addCollection(
                    new RuntimePermission("createClassLoader"), // this one is explicitly denied below, it's really
                                                                // allowed on the SecurePythonProtectionDomain only
                    new RuntimePermission("getProtectionDomain"),
                    new RuntimePermission("accessDeclaredMembers"),
                    new RuntimePermission("accessClassInPackage.sun.reflect"))
            .addCollection(
                    new PropertyPermission("user.*", "read"),
                    new PropertyPermission("line.separator", "read"))
            .build();
    static final PermissionCollection DENIED_PERMISSIONS = SimplePermissionCollection.builder()
            .addCollection(
                    new RuntimePermission("exitVM"),
                    new RuntimePermission("createClassLoader"))
            .build();

    @Override
    protected PermissionCollection getAllowedPermissions() {
        return ALLOWED_PERMISSIONS;
    }

    @Override
    protected PermissionCollection getDeniedPermissions() {
        return DENIED_PERMISSIONS;
    }

    @Override
    protected String[] getProtectedPackages() {
        return PYTHON_PKG;
    }

    @Override
    protected String[] getExcludedPackages() {
        return EXCLUDED_PKGS;
    }
}
