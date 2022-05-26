package com.pawcie.authorization.security;

import com.pawcie.authorization.users.ApplicationUserDetailsService;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.TimeUnit;

import static com.pawcie.authorization.security.ApplicationPermissions.PRODUCTS_WRITE;
import static com.pawcie.authorization.security.ApplicationRoles.*;

@Configuration
@AllArgsConstructor
public class ApplicationSecurityConfig {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authenticationProvider(authenticationProvider())
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/", "index", "/css/*", "/js/*").permitAll() //whitelist specific parameters
                        .antMatchers("/products/all").hasRole(ADMIN.name())
                        .antMatchers("/users/all").hasRole(ADMIN.name())
                        .antMatchers("/products/published/**").permitAll()
                        .antMatchers("/products/unpublished/**").hasRole(ADMIN.name())
                        .antMatchers("/contact/**").permitAll()
                        .anyRequest()
                        .authenticated())
                //.httpBasic(Customizer.withDefaults())
                .formLogin(formLoginConfigurer -> {
                    try {
                        formLoginConfigurer.defaultSuccessUrl("/users/all", true)
                                .and().rememberMe()
                                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                                .key("some custom key private and unique");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("logout") // default behaviour
                        .deleteCookies("JSESSIONID", "remember-me")) // default behaviour
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails pawcio = User.builder()
                .username("Pawcio")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMIN.getAuthorities())
                .build();

        UserDetails kamilKulturysta = User.builder()
                .username("Kamczii")
                .password(passwordEncoder.encode("password123"))
                .authorities(USER.getAuthorities())
                .build();
        UserDetails kubaModel = User.builder()
                .username("mlody_jakub")
                .password(passwordEncoder.encode("zalando123"))
                .authorities(USER.getAuthorities())
                .build();
        return new InMemoryUserDetailsManager(pawcio, kamilKulturysta, kubaModel);
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserDetailsService);
        return provider;
    }
}
