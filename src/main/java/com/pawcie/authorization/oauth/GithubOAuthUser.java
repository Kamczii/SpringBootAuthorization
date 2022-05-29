package com.pawcie.authorization.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

import static com.pawcie.authorization.security.ApplicationRoles.USER;

@Getter
@Setter
public class GithubOAuthUser implements OAuth2User {

    private Set<SimpleGrantedAuthority> authorities = USER.getAuthorities();
    private Map<String, Object> attributes;
    private String id;
    private String name;
    private String login;
    private String email;

    @Override
    public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
            this.attributes.put("id", this.getId());
            this.attributes.put("name", this.getName());
            this.attributes.put("login", this.getLogin());
            this.attributes.put("email", this.getEmail());
        }
        return attributes;
    }
}
