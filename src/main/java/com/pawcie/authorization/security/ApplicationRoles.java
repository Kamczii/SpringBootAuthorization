package com.pawcie.authorization.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.pawcie.authorization.security.ApplicationPermissions.*;

@AllArgsConstructor
public enum ApplicationRoles {
    ADMIN(Sets.newHashSet(USERS_WRITE, USERS_READ, PRODUCTS_WRITE, PRODUCTS_READ)),
    CUSTOMER(Sets.newHashSet(PRODUCTS_READ)),
    SUPPORT(Sets.newHashSet(USERS_READ, PRODUCTS_READ));

    @Getter
    private final Set<ApplicationPermissions> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
        return authorities;
    }
}
