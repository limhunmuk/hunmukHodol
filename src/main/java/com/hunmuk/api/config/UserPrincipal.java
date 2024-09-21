package com.hunmuk.api.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(com.hunmuk.api.domain.User user, Long userId) {
        super(user.getEmail(), user.getPassword(),
                List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN")
                , new SimpleGrantedAuthority("WRITE")
                ));
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
