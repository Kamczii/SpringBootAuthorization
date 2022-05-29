package com.pawcie.authorization.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.pawcie.authorization.entities.User;
import com.pawcie.authorization.services.UserService;
import com.pawcie.authorization.users.ApplicationUser;
import com.pawcie.authorization.users.ApplicationUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.apache.catalina.Role;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping(path = "token")
@AllArgsConstructor
public class JwtTokenRefresh {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
            try {
                String refreshToken = new ObjectMapper()
                        .readValue(request.getInputStream(), String.class);;

                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(jwtConfig.getSecretKey()).build().parseClaimsJws(refreshToken);

                String username = claimsJws.getBody().getSubject();

                ApplicationUser user = applicationUserDetailsService.loadUserByUsername(username);

                String access_token = Jwts.builder()
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + 2 * 1000 * 60 ))
                        .claim("authorities", user.getAuthorities())
                        .signWith(jwtConfig.getSecretKey())
                        .compact();
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception e){
                response.setHeader("error ", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
    }
}
