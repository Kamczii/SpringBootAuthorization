package com.pawcie.authorization.security;

import com.pawcie.authorization.jwt.JwtConfig;
import com.pawcie.authorization.jwt.JwtTokenVerifier;
import com.pawcie.authorization.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.pawcie.authorization.users.ApplicationUserDetailsService;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    private final JwtConfig jwtConfig;

    @Override
    public void configure (HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // sesja nie będzie przechowywana w bazie
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll() //whitelist specific parameters
                .antMatchers("/products/all").hasRole(ADMIN.name())
                .antMatchers("/users/all").hasRole(ADMIN.name())
                .antMatchers("/products/published/**").permitAll()
                .antMatchers("/products/unpublished/**").hasRole(ADMIN.name())
                .antMatchers("/contact/**").permitAll()
                .anyRequest()
                .authenticated();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails pawcio = User.builder()
//                .username("Pawcio")
//                .password(passwordEncoder.encode("password"))
//                .authorities(ADMIN.getAuthorities())
//                .build();
//
//        UserDetails kamilKulturysta = User.builder()
//                .username("Kamczii")
//                .password(passwordEncoder.encode("password123"))
//                .authorities(USER.getAuthorities())
//                .build();
//        UserDetails kubaModel = User.builder()
//                .username("mlody_jakub")
//                .password(passwordEncoder.encode("zalando123"))
//                .authorities(USER.getAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(pawcio, kamilKulturysta, kubaModel);
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserDetailsService);
        return provider;
    }
}
