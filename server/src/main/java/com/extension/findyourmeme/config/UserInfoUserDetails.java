package com.extension.findyourmeme.config;

import com.extension.findyourmeme.dao.UserDao;
import com.extension.findyourmeme.entity._User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserInfoUserDetails implements UserDetails{

    private UserDao userDao;
    private String username;
    private String password;
    private GrantedAuthority authority;
    public UserInfoUserDetails(_User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authority = new SimpleGrantedAuthority(user.getRole());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
        return true;
    }
}
