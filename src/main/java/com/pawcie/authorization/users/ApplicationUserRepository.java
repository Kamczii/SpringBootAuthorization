package com.pawcie.authorization.users;

import com.pawcie.authorization.entities.ApplicationUser;

import java.util.List;
import java.util.Optional;

public interface ApplicationUserRepository {
    Optional<ApplicationUser> loadUserByUsername(String username);
    List<ApplicationUser> loadUsers();
}
