package com.madratz.security;

import java.security.Permission;

public class MadratzSecurityManager extends SecurityManager {

    public static void setSystemSecurityManagerIfNeeded() {
        synchronized (MadratzSecurityManager.class) {
            if (!(System.getSecurityManager() instanceof MadratzSecurityManager)) {
                System.setSecurityManager(new MadratzSecurityManager(System.getSecurityManager()));
            }
        }
    }

    private final SecurityManager mPreviousSecurityManager;

    public MadratzSecurityManager() {
        this(null);
    }

    public MadratzSecurityManager(SecurityManager previousSecurityManager) {
        mPreviousSecurityManager = previousSecurityManager;
    }

    @Override
    public void checkPermission(Permission perm) {
        Class[] classContext = getClassContext();
        if (isRecursive(classContext)) return; // give ourselves any permission we need
        for (PackageProtectionDomain ppd : PackageProtectionDomain.getDomains()) {
            if (ppd.applies(classContext) && !ppd.implies(perm)) {
                throw new SecurityException("Permission denied: " + perm);
            }
        }
        if (mPreviousSecurityManager != null) mPreviousSecurityManager.checkPermission(perm);
    }

    private boolean isRecursive(Class[] classContext) {
        // start from 1, the first class in the context will be ourselves
        for (int i = 1; i < classContext.length; i++) {
            if (classContext[i].equals(MadratzSecurityManager.class)) {
                return true;
            }
        }
        return false;
    }
}
