package com.pawcie.authorization.services;

import com.pawcie.authorization.entities.User;
import com.pawcie.authorization.utilities.ForbiddenException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {


    public List<User> getUsers(){
        return List.of(
                User.builder()
                .id(0)
                .fullname("Pawel D")
                .nick("Pawcio")
                .birthday(new Date(2000 - 1900,05,19))
                .build(),

                User.builder()
                        .id(1)
                        .fullname("Kamil C")
                        .nick("Kamczii")
                        .birthday(new Date(2000 - 1900,04,04))
                        .build(),

                User.builder()
                        .id(2)
                        .fullname("Jakub G")
                        .nick("mlody_jakub")
                        .birthday(new Date(2000 - 1900,02,02))
                        .build()
        );
    }


//check if admin
    private boolean checkUser(String username, Integer id){
            User user = getUsers().stream()
                    .filter(u -> username.equals(u.getNick()))
                    .filter(u -> id.equals(u.getId()))
                    .findFirst()
                    .orElseThrow(ForbiddenException::new);

        return true;
    }

//map date with stream
    public User getUser(Integer id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        checkUser(username, id);
        return getUsers().stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User "+id+" does not exists"));
    }
}