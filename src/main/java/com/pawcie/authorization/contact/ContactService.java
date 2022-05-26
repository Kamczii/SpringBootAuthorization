package com.pawcie.authorization.contact;

import org.springframework.stereotype.Service;

@Service
public class ContactService {

    public Contact getContact(){
        return Contact.builder()
                .address("Zielona 25")
                .city("Gdansk")
                .country("Poland")
                .number("123-456-789")
                .email("contact@pg.edu.pl")
                .build();
    }
}
