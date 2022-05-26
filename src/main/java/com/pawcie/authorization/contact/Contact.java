package com.pawcie.authorization.contact;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Contact {

    String number;
    String address;
    String email;
    String city;
    String country;
}
