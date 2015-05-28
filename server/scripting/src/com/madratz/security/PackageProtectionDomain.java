package com.madratz.security;

import com.madratz.security.domains.PythonProtectionDomain;
import com.madratz.security.domains.SecurePythonProtecionDomain;

import java.security.Permission;

public interface PackageProtectionDomain {

    public boolean implies(Permission perm);

    public boolean applies(Class[] classContext);

    public static PackageProtectionDomain[] getDomains() {
        return new PackageProtectionDomain[]{
                new PythonProtectionDomain(),
                new SecurePythonProtecionDomain()
        };
    }
}
