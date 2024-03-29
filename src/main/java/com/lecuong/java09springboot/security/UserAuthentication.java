package com.lecuong.java09springboot.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private UserDetails user;

    public UserAuthentication(UserDetails user) {
        this.user = user;
    }

    public String getName() {
        return null;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getAuthorities();
    }

    public Object getCredentials() {
        return null;
    }

    public Object getDetails() {
        return this.user;
    }

    public Object getPrincipal() {
        return null;
    }

    public boolean isAuthenticated() {
        return this.user != null && !StringUtils.isEmpty(this.user.getUser().getId());
    }

    public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
    }
}
