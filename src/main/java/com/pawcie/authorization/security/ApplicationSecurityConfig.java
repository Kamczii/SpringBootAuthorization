package com.pawcie.authorization.security;

import com.pawcie.authorization.users.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.concurrent.TimeUnit;

import static com.pawcie.authorization.security.ApplicationPermissions.PRODUCTS_WRITE;
import static com.pawcie.authorization.security.ApplicationRoles.*;

@Configuration
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    private final ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired // Wstrzyknięcie przez konstruktor umożliwia ustawienie pola jako final, co jest dobrą praktyką.
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserDetailsService applicationUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserDetailsService = applicationUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authenticationProvider(authenticationProvider())
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/users/**").hasRole(ADMIN.name())
                        .antMatchers(HttpMethod.POST, "/products/**").hasAuthority(PRODUCTS_WRITE.name())
                        .antMatchers(HttpMethod.DELETE, "/products/**").hasAuthority(PRODUCTS_WRITE.name())
                        .antMatchers(HttpMethod.PUT, "/products/**").hasAuthority(PRODUCTS_WRITE.name())
                        .antMatchers("/products/**").hasRole(CUSTOMER.name())
                        .anyRequest().permitAll())
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

//    /** Do wyciągania użytkowników z bazy danych */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails customer = User.builder()
//                .username("customer")
//                .password(passwordEncoder.encode("pass"))
//                // .roles(CUSTOMER.name()) // ROLE_CUSTOMER
//                .authorities(CUSTOMER.getAuthorities())
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("pass"))
//                // .roles(ADMIN.name()) //ROLE_ADMIN
//                .authorities(ADMIN.getAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(admin,customer);
//    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserDetailsService);
        return provider;
    }
}
