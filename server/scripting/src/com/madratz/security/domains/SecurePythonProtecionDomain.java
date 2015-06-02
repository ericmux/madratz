package com.madratz.security.domains;

import com.madratz.security.permissions.SimplePermissionCollection;

import java.security.PermissionCollection;

public class SecurePythonProtecionDomain extends SimpleProtectionDomain {

    private static final SimplePermissionCollection EMPTY_PERMISSIONS = new SimplePermissionCollection();
    private static final String[] EMPTY_PKGS = new String[0];

    @Override
    protected PermissionCollection getAllowedPermissions() {
        return PythonProtectionDomain.ALLOWED_PERMISSIONS;
    }

    @Override
    protected PermissionCollection getDeniedPermissions() {
        return EMPTY_PERMISSIONS;
    }

    @Override
    protected String[] getProtectedPackages() {
        return PythonProtectionDomain.PYTHON_PKG;
    }

    @Override
    protected String[] getExcludedPackages() {
        return EMPTY_PKGS;
    }
}
