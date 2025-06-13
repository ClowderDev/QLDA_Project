package com.example.QLDA_Project.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(User user, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.user = user;
        this.authorities = authorities;
    }

    public CustomUserDetail() {
        super();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    public User getUser() {
        return user;
    }
}
