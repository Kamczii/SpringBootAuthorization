package com.pawcie.authorization.security;

import com.pawcie.authorization.jwt.JwtConfig;
import com.pawcie.authorization.jwt.JwtTokenVerifier;
import com.pawcie.authorization.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.pawcie.authorization.oauth.GithubOAuthUser;
import com.pawcie.authorization.services.ApplicationUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.pawcie.authorization.security.ApplicationRoles.*;

@AllArgsConstructor
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    private final JwtConfig jwtConfig;

    @Configuration
    @Order(1)
    public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf().disable()
                    .antMatcher("/api/**")
                    .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                    .addFilterAfter(new JwtTokenVerifier(jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/api/private/**").hasAnyRole(ADMIN.name(), USER.name()) //TODO: komunikat zamiast przekierowania na logowanie
                    .antMatchers("/api/public/**").permitAll()
                    .anyRequest().denyAll();
        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) {
//            auth.authenticationProvider(ApplicationSecurityConfig.this.authenticationProvider());
//        }
    }

    @Configuration
    public class FormLoginSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        public void configure (HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(
                            "/",
                            "index",
                            "/css/*",
                            "/js/*",
                            "/images/**",
                            "/products/published/**",
                            "/contact/**",
                            "/token/refresh",
                            "/login").permitAll() //whitelist specific parameters
                    .antMatchers(
                            "/products/all",
                            "/users/all",
                            "/products/unpublished/**").hasRole(ADMIN.name())
                    .antMatchers("/users/all").hasRole(ADMIN.name())
                    .antMatchers("/products/published/**").permitAll()
                    .antMatchers("/products/unpublished/**").hasRole(ADMIN.name())
                    .antMatchers("/contact/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
                    .and()
                    .oauth2Login().userInfoEndpoint()
                    .customUserType(GithubOAuthUser.class, "google")
                    .customUserType(GithubOAuthUser.class, "github").and().loginPage("/login").defaultSuccessUrl("/").permitAll();
        }

//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) {
//            auth.authenticationProvider(ApplicationSecurityConfig.this.authenticationProvider());
//        }

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserDetailsService);
        return provider;
    }
}
