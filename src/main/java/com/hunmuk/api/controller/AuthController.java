package com.hunmuk.api.controller;

import com.hunmuk.api.config.AppConfig;
import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.request.Login;
import com.hunmuk.api.request.Signup;
import com.hunmuk.api.response.SessionResponse;
import com.hunmuk.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private final UserRepository userRepository;
    private final AuthService authService;
    private final AppConfig appConfig;
    private final static String KEY = "U2grUHZhL2JwZmpKSy9wSFlrbGxFdGMvMVkwNWVjNG02UDNmRHlDalhDMD0=";



    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
    //public String login(@RequestBody Login login) {

        log.info("login = " + login);
        Long userId = authService.signIn(login);

        //이렇게 하면 요청이 올 때마다 key 값이 다르게 저장된다. 따라서 일반적으로는 고정하여 사용한다.
        //  Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        /**
         Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        byte [] encodedKey  = key.getEncoded();
        String strKey = Base64.getEncoder().encodeToString(encodedKey);
         */
        SecretKey secretKey = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts
                .builder()
                .subject(String.valueOf(userId))
                .signWith(secretKey)
                .setIssuedAt(new java.util.Date())
                .compact();

        System.out.println("jws = " + jws);
        System.out.println("userId = " + userId);

        return new SessionResponse(jws);

    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup login) {
        authService.signup(login);
    }
}
