package com.madratz.security.permissions;

import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

public class SimplePermissionCollection extends PermissionCollection {

    public static Builder builder() {
        return new Builder();
    }

    private final Vector<Permission> mPermissions;
    private final Vector<PermissionCollection> mPermissionCollections;

    public SimplePermissionCollection() {
        this(Collections.emptyList(), Collections.emptyList());
    }

    public SimplePermissionCollection(Collection<Permission> permissions, Collection<PermissionCollection> permissionCollections) {
        mPermissions = new Vector<>(permissions);
        mPermissionCollections = new Vector<>(permissionCollections);
    }

    @Override
    public void add(Permission perm) {
        if (isReadOnly()) throw new IllegalStateException("PermissionCollection is read-only");
        mPermissions.add(perm);
    }

    public void add(PermissionCollection collection) {
        if (isReadOnly()) throw new IllegalStateException("PermissionCollection is read-only");
        mPermissionCollections.add(collection);
    }

    @Override
    public boolean implies(Permission perm) {
        for (Permission implying : mPermissions) {
            if (implying.implies(perm)) {
                return true;
            }
        }
        for (PermissionCollection implying : mPermissionCollections) {
            if (implying.implies(perm)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Enumeration<Permission> elements() {
        Vector<Permission> allPerms = new Vector<>();
        allPerms.addAll(mPermissions);
        mPermissionCollections.stream()
                .map(PermissionCollection::elements)
                .map(Collections::list)
                .forEach(allPerms::addAll);
        return allPerms.elements();
    }

    public static class Builder {
        private Vector<Permission> mPermissions = new Vector<>();
        private Vector<PermissionCollection> mPermissionCollections = new Vector<>();

        public PermissionCollection build() {
            PermissionCollection c = new SimplePermissionCollection(mPermissions, mPermissionCollections);
            c.setReadOnly();
            return c;
        }

        public Builder add(Permission permissions) {
            mPermissions.add(permissions);
            return this;
        }

        public Builder add(PermissionCollection collection) {
            mPermissionCollections.add(collection);
            return this;
        }

        public Builder addCollection(Permission... permissions) {
            if (permissions.length == 0) return this;
            PermissionCollection collection = permissions[0].newPermissionCollection();
            if (collection == null) collection = new SimplePermissionCollection();

            for (Permission p : permissions) {
                collection.add(p);
            }
            return add(collection);
        }
    }
}
