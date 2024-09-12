package com.hunmuk.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

public class Base64Test {

    @Test
    @DisplayName("")
    void testCase1(){
        String text = "U2grUHZhL2JwZmpKSy9wSFlrbGxFdGMvMVkwNWVjNG02UDNmRHlDalhDMD0=";
        byte[] targetBytes = text.getBytes();

        // Base64 인코딩 ///////////////////////////////////////////////////
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(targetBytes);

        // Base64 디코딩 ///////////////////////////////////////////////////
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedBytes);

        System.out.println("인코딩 전 : " + text);
        System.out.println("인코딩 text : " + new String(encodedBytes));
        System.out.println("디코딩 text : " + new String(decodedBytes));
    }

    String KEY = "q1SmKvKnFUXhptudbtdDJMoZs8MIAmUErxU1vr1mXyc";
    @Test
    public void test11(){

        String userId = "1";
        SecretKey secretKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(KEY));

        /*Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        byte [] encodedKey  = key.getEncoded();
        String strKey = Base64.getEncoder().encodeToString(encodedKey);

        System.out.println("key = " + strKey);*/
        String jws = io.jsonwebtoken.Jwts
                .builder()
                .subject(userId)
                .signWith(secretKey)
                .compact();
        System.out.println("jws = " + jws);
    }

    @Test
    public void test12() {

        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.imNx9pEk0YNeVTjxF4eTKhCxXLpPhYJeSWkhl_UfAqY";

        byte[] decodedKey = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(KEY); //스트링 값이었던 것을 바이트 값으로 변환한다. setSigningKey(String)이 폐기되어서.

        Jws<Claims> claims = Jwts.parser()
//                    .setSigningKey(KEY) //사인값 추가 deprecated
                .setSigningKey(decodedKey) //파라미터로 바이트 값이 들어간다.
                .build()
                .parseClaimsJws(jwt);

        String userId = claims.getBody().getSubject();
        System.out.println("userId = " + userId);
    }

}
