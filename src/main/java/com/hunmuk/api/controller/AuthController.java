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
import java.security.Key;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private final UserRepository userRepository;
    private final AuthService authService;
    private final static String KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.x6bowyHqNQjI9lvjxHe9MHwJMoSnvBETdgDI6EWk9rE";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
    //public String login(@RequestBody Login login) {

 /*       log.info("login = " + login);
        String accessToken = authService.signIn(login);
        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(30)) // 30일
                .build();

        log.info("cookie = " + cookie.toString()    );
*/

        //key 는 고정값으로 사용해야 됨
        //SecretKey key = Jwts.SIG.HS256.key().build();
        //byte[] keyEncoded = key.getEncoded();
        // base64 는 바이트를 스트링으로 변환해주느 녀석
        //String strKey = Base64.getEncoder().encodeToString(keyEncoded);
        //log.info("key = " + strKey);
        //B+6oBJ9PPdHQgrl4qBMll4tVtGgSDKue6pPfzhdKnWE=
        Key savekey = Keys.hmacShaKeyFor(KEY.getBytes());
        String jws = Jwts.builder().subject("Joe").signWith(getSigningKey()).compact();

        /*return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();*/

        return new SessionResponse(jws);

    }
}
