package com.pawcie.authorization.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pawcie.authorization.security.ApplicationRoles.ADMIN;
import static com.pawcie.authorization.security.ApplicationRoles.CUSTOMER;

@Repository("mock")
public class MockUserDetailsRepository implements ApplicationUserRepository{

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MockUserDetailsRepository(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<ApplicationUser> loadUserByUsername(String username) {
        return getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    private List<ApplicationUser> getUsers() {
        return List.of(
                new ApplicationUser("customer",
                        bCryptPasswordEncoder.encode("pass"),
                        CUSTOMER.getAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser("admin",
                        bCryptPasswordEncoder.encode("pass"),
                        ADMIN.getAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );
    }
}
