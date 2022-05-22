package com.pawcie.authorization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.pawcie.authorization.security.ApplicationPermissions.PRODUCTS_WRITE;
import static com.pawcie.authorization.security.ApplicationRoles.*;

@Configuration
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired // Wstrzyknięcie przez konstruktor umożliwia ustawienie pola jako final
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/users/**").hasRole(ADMIN.name())
                        .antMatchers(HttpMethod.POST, "/products/**").hasAuthority(PRODUCTS_WRITE.name())
                        .antMatchers(HttpMethod.DELETE, "/products/**").hasAuthority(PRODUCTS_WRITE.name())
                        .antMatchers(HttpMethod.PUT, "/products/**").hasAuthority(PRODUCTS_WRITE.name())
                        .antMatchers("/products/**").hasRole(CUSTOMER.name())
                        .anyRequest().permitAll())
                //.httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    /** Do wyciągania użytkowników z bazy danych */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails customer = User.builder()
                .username("customer")
                .password(passwordEncoder.encode("pass"))
                // .roles(CUSTOMER.name()) // ROLE_CUSTOMER
                .authorities(CUSTOMER.getAuthorities())
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("pass"))
                // .roles(ADMIN.name()) //ROLE_ADMIN
                .authorities(ADMIN.getAuthorities())
                .build();
        return new InMemoryUserDetailsManager(admin,customer);
    }
}
