package com.hunmuk.api.config;

import com.hunmuk.api.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

   @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                //.allowedOrigins("http://localhost:5174")
                .allowedOrigins("http://localhost:5173")

                //.allowCredentials(true)
                //.maxAge(3600)
                ;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      /*  registry.addInterceptor(new AuthIntercepter())
                .excludePathPatterns("/error", "/favicon.ico")
                //.excludePathPatterns("/poo")
        ;*/
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
       resolvers.add(new AuthResolver(sessionRepository, appConfig));
    }
}
