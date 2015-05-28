package com.madratz.security.domains;

import com.madratz.security.PackageProtectionDomain;
import com.madratz.security.Privileged;

import java.security.Permission;
import java.security.PermissionCollection;

/**
 * The SimpleProtectionDomain provides a simpler interface for implementing a custom
 * PackageProtectionDomain in which implementers must only provide values for a group
 * of packages that are to be under this domain and a group that should be excluded,
 * and a collection of allowed and denied permissions.
 * The ProtectionDomain is then said to imply a certain Permission if and only if that
 * permission is in the allowed permissions collection AND it is not in the denied
 * collection. And to say if the domain applies to a certain class context, the
 * following should happen:
 *  - A class inside the given protected packages ({@link SimpleProtectionDomain#
 *  getProtectedPackages()}) is in the class context and is not followed by one from
 *  the excluded packages ({@link SimpleProtectionDomain#getExcludedPackages()}. 2
 *  things to note here:
 *     1. The excluded packages might be sub-packages of the protected packages. In this
 *        case it's just not considered to be in the allowed packages;
 *     2. If a class from the excluded package is followed in the stack by one from the
 *        protected package (that is not excluded), the domain will apply. The rule of
 *        thumb is that the deepest class in the context to be either in the allowed or
 *        excluded packages will determine the appliance of the domain.
 *  - UNLESS somewhere in the stack some method was called using the {@link Privileged}
 *    class. When the Privileged class is in the stack, only the classes above that call
 *    in the stack are considered as determining if the domain will apply to the context.
 *    This is to call code you trust from a package that you want to be sandboxed elsewhere.
 *    (which is different from Java's {@link java.security.AccessController#doPrivileged}).
 */
public abstract class SimpleProtectionDomain implements PackageProtectionDomain {

    @Override
    public boolean implies(Permission perm) {
        return getAllowedPermissions().implies(perm) && !getDeniedPermissions().implies(perm);
    }

    @Override
    public boolean applies(Class[] classContext) {
        boolean applies = false;
        for (Class c : classContext) {
            String name = c.getName();
            if (startsWithAny(name, getExcludedPackages()) && !applies) return false;
            if (startsWithAny(name, getProtectedPackages())) applies = true;
            if (c.equals(Privileged.class)) applies = false;
        }
        return applies;
    }

    protected abstract String[] getProtectedPackages();

    protected abstract String[] getExcludedPackages();

    private boolean startsWithAny(String s, String[] prefixes) {
        for (String prefix : prefixes) {
            if (s.startsWith(prefix)) return true;
        }
        return false;
    }

    protected abstract PermissionCollection getAllowedPermissions();

    protected abstract PermissionCollection getDeniedPermissions();
}
