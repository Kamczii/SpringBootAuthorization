package com.pawcie.authorization.security;

public enum ApplicationPermissions {
    PRODUCTS_READ("products:read"),
    PRODUCTS_WRITE("products:write"),
    USERS_READ("users:read"),
    USERS_WRITE("products:write");

    private final String permission;

    ApplicationPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
