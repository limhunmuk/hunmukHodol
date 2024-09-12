package com.hunmuk.api.crpto;

public interface PasswordEncoder {

    public String encrypt(String password);

    public boolean matches(String rawPassword, String encodedPassword) ;
}
