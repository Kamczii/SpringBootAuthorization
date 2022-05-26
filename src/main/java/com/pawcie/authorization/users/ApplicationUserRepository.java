package com.pawcie.authorization.users;

import java.util.Optional;

public interface ApplicationUserRepository {
    Optional<ApplicationUser> loadUserByUsername(String username);
}
