package com.hunmuk.api.config;

import com.hunmuk.api.HunmukPermissionEvaluator;
import com.hunmuk.api.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MethodSecurityConfig {

    private final PostRepository postRepository;
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
      var handler = new DefaultMethodSecurityExpressionHandler();
      handler.setPermissionEvaluator(new HunmukPermissionEvaluator(postRepository));
      return handler;
    }

}
