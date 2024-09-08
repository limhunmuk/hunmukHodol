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
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.crypto.SecretKey;

/**
 * description : 인증처리를 위한 Resolver
 * UserSession 객체를 생성하여 반환
 * 인증된 사용자 유무 판단
 */
@RequiredArgsConstructor
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;
    private final static String KEY = "U2grUHZhL2JwZmpKSy9wSFlrbGxFdGMvMVkwNWVjNG02UDNmRHlDalhDMD0=";

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

        System.out.println("appConfig.hello.getName() = " + appConfig.hello.getName());
        System.out.println("appConfig.getHello() = " + appConfig.getHello());

        if(jws == null || jws.isEmpty()) {
            throw new UnAuthorized();
        }




        try {
            String key = Base64.encodeBase64String(KEY.getBytes());
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(jws);

            //OK, we can trust this JWT -> 인증됨

            log.info("claims = {} " + claims);
            String userId = claims.getBody().getSubject();
            return new UserSession(Long.valueOf(userId));

        } catch (JwtException e) {
            throw new UnAuthorized();
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

    }
}
