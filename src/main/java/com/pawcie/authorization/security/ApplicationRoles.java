package com.pawcie.authorization.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.pawcie.authorization.security.ApplicationPermissions.*;

public enum ApplicationRoles {
    ADMIN(Sets.newHashSet(USERS_WRITE, USERS_READ, PRODUCTS_WRITE, PRODUCTS_READ)),
    CUSTOMER(Sets.newHashSet(PRODUCTS_READ)),
    SUPPORT(Sets.newHashSet(USERS_READ, PRODUCTS_READ));

    private final Set<ApplicationPermissions> permissions;

    ApplicationRoles(Set<ApplicationPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return authorities;
    }
}
