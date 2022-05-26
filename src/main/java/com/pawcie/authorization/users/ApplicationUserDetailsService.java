package com.pawcie.authorization.users;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {


    private final ApplicationUserRepository userRepository;

    public ApplicationUserDetailsService(@Qualifier("mock") ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ApplicationUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.loadUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
