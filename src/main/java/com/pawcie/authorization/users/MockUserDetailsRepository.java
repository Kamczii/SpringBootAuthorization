package com.pawcie.authorization.users;

import com.pawcie.authorization.entities.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pawcie.authorization.security.ApplicationRoles.ADMIN;
import static com.pawcie.authorization.security.ApplicationRoles.USER;

@Repository("mock")
public class MockUserDetailsRepository implements ApplicationUserRepository {

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MockUserDetailsRepository(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<ApplicationUser> loadUserByUsername(String username) {
        return getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    public List<ApplicationUser> loadUsers() {
        return getUsers();
    }

    private List<ApplicationUser> getUsers() {
        return List.of(
                new ApplicationUser(1,
                        "admin",
                        bCryptPasswordEncoder.encode("password"),
                        ADMIN.getAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(2,
                        "customer",
                        bCryptPasswordEncoder.encode("password"),
                        USER.getAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser(3,
                        "customer2",
                        bCryptPasswordEncoder.encode("password"),
                        USER.getAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );
    }
}
