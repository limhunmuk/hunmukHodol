package com.hunmuk.api.crpto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class PlainPasswordEncoder implements PasswordEncoder {

    @Override
    public String encrypt(String password) {
        return password;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
