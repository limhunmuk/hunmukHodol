package com.hunmuk.api.controller;

import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.request.Login;
import com.hunmuk.api.response.SessionResponse;
import com.hunmuk.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
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
    private final static String KEY = "U2grUHZhL2JwZmpKSy9wSFlrbGxFdGMvMVkwNWVjNG02UDNmRHlDalhDMD0=";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
    //public String login(@RequestBody Login login) {

        log.info("login = " + login);
        Long userId = authService.signIn(login);

        //key 는 고정값으로 사용해야 됨
        // 키 생성 - > 유출되면 안됨
        //byte[] keyEncoded = key.getEncoded();
        //String strKey = Base64.getEncoder().encodeToString(keyEncoded);

        //System.out.println("strKey = " + strKey);

        String jws = Jwts
                .builder()
                .subject(String.valueOf(userId))
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .compact();
        System.out.println("jws = " + jws);
        System.out.println("userId = " + userId);

        return new SessionResponse(jws);

    }
}
