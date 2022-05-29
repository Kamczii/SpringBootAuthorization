package com.pawcie.authorization.services;

import com.pawcie.authorization.entities.ApplicationUser;
import com.pawcie.authorization.users.ApplicationUserRepository;
import com.pawcie.authorization.utilities.ForbiddenException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ApplicationUser> loadUsers() {
        return userRepository.loadUsers();
    }

    private boolean checkUser(String username, Integer id){
        ApplicationUser user = userRepository.loadUsers().stream()
                .filter(u -> username.equals(u.getUsername()))
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElseThrow(ForbiddenException::new);

        return true;
    }

    //map date with stream
    public ApplicationUser loadUserById(Integer id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        checkUser(username, id);
        return userRepository.loadUsers().stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User "+id+" does not exists"));
    }
}
