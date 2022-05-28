package com.pawcie.authorization.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@ConfigurationProperties(prefix = "application.jwt")
@Getter
@Setter
@NoArgsConstructor
@Configuration
public class JwtConfig {
    String prefix;
    String header;
    String secretKey;

    public SecretKey getSecretKey()
    {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
