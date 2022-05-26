package com.pawcie.authorization.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
public class User {

    private short id;
    private String fullname;
    private String nick;
    private Date birthday;


}
