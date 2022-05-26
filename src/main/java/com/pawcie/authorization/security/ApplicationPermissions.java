package com.pawcie.authorization.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApplicationPermissions {
    PRODUCTS_READ("products:read"),
    PRODUCTS_WRITE("products:write"),
    USERS_READ("users:read"),
    USERS_WRITE("users:write");

    @Getter
    private final String permission;
}
