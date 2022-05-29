package com.pawcie.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.security.core.SpringSecurityCoreVersion;

@SpringBootApplication
public class AuthorizationApplication {

	public static void main(String[] args) {
		SpringSecurityCoreVersion.getVersion();
		SpringApplication.run(AuthorizationApplication.class, args);
	}

}
