package com.pawcie.authorization.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JwtAuthenticationRequest {
    String username;
    String password;
}
