package com.rustam.magbackend.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rustam.magbackend.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class AccountBaseImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final Long id;

    @JsonIgnore
    private final String username;

    @JsonIgnore
    private final String encryptedPassword;

    private final Boolean isLocked;

    List<? extends GrantedAuthority> authorities;

    public AccountBaseImpl(Long id, String username, String encryptedPassword, Boolean isLocked, List<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.authorities = authorities;
        this.isLocked = isLocked;
    }

    public static AccountBaseImpl build(Account account, List<? extends GrantedAuthority> authorities){
        return new AccountBaseImpl(
                account.getId(),
                account.getUsername(),
                account.getEncryptedPassword(),
                account.getLocked(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
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

