package com.pawcie.authorization.users;

import com.pawcie.authorization.users.ApplicationUser;
import com.pawcie.authorization.users.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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

    private List<ApplicationUser> getUsers() {
        return List.of(
                new ApplicationUser("Pawcio",
                        bCryptPasswordEncoder.encode("password"),
                        ADMIN.getAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser("Kamczi",
                        bCryptPasswordEncoder.encode("password123"),
                        USER.getAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser("mlody_jakub",
                        bCryptPasswordEncoder.encode("zalando123"),
                        USER.getAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );
    }
}
