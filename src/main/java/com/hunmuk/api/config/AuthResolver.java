package com.hunmuk.api.config;

import com.hunmuk.api.config.data.UserSession;
import com.hunmuk.api.exception.UnAuthorized;
import com.hunmuk.api.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.crypto.SecretKey;
import java.util.Base64;

@RequiredArgsConstructor
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final static String KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.x6bowyHqNQjI9lvjxHe9MHwJMoSnvBETdgDI6EWk9rE";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jws = webRequest.getHeader("authorization");

        if(jws == null || jws.isEmpty()) {
            throw new UnAuthorized();
        }

        byte[] decKEY = Base64.getEncoder().encode(KEY.getBytes());

        try {

            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(jws);

            //OK, we can trust this JWT

            log.info("claims = {} " + claims);



        } catch (JwtException e) {
            throw new UnAuthorized();
            //don't trust the JWT!
        }


     /**  HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        Cookie[] cookies = servletRequest.getCookies();
        if(cookies == null) {
            log.error("쿠키가 없습니다.");
            throw new UnAuthorized();
        }
        for (Cookie cookie : cookies) {
            System.out.println("cookie = " + cookie.getName() + " : " + cookie.getValue());
        }

        String accessToken = cookies[0].getValue();
        if(accessToken == null || accessToken.isEmpty()) {
            throw new UnAuthorized();
        }
      */

//        Session session = sessionRepository.findByAccessToken(accessToken).orElseThrow(UnAuthorized::new);

        return null;


    }
}
