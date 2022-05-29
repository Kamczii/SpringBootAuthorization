package com.pawcie.authorization.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtConfig jwtConfig;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            JwtAuthenticationRequest rq = new ObjectMapper()
                    .readValue(request.getInputStream(), JwtAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(rq.getUsername(), rq.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String accessToken = Jwts.builder()
                .setSubject(authResult.getName())
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(2)))
                .claim("authorities",authResult.getAuthorities())
                .signWith(jwtConfig.getSecretKey())
                .compact();
        String refreshToken = Jwts.builder()
                .setSubject(authResult.getName())
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(30)))
                .signWith(jwtConfig.getSecretKey())
                .compact();
//        response.setHeader("access_token", accessToken);
//        response.setHeader("refresh_token", refreshToken);

        Map<String, String> tokens = new HashMap<String, String>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

    }
}
