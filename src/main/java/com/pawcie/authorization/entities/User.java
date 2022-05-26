package com.pawcie.authorization.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
public class User {

    private Integer id;
    private String fullname;
    private String nick;
    private Date birthday;


}
