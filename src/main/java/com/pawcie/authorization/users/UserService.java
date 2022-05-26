package com.pawcie.authorization.users;

import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class UserService {


    public List<User> getUsers(){
        return List.of(
                User.builder()
                .id((short)0)
                .fullname("Pawel D")
                .nick("Protex")
                .birthday(new Date(2000 - 1900,05,19))
                .build(),

                User.builder()
                        .id((short)0)
                        .fullname("Kamil C")
                        .nick("Kamczii")
                        .birthday(new Date(2000 - 1900,04,04))
                        .build(),

                User.builder()
                        .id((short)0)
                        .fullname("Jakub G")
                        .nick("mlody_jakub")
                        .birthday(new Date(2000 - 1900,02,02))
                        .build()
        );
    }



public User getUser(Integer id){
        return getUsers().stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User "+id+" does not exists"));
    }
}
