package com.hunmuk.api.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = HunmukMockUserSecurityContextFactory.class)
public @interface HunmukMockUser {

    String email() default "ihm2119@naver.com";
    String password() default "";
    String name() default "훈묵쓰";

    String role() default "ROLE_ADMIN";

}
