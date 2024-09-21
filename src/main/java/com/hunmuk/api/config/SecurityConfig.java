package com.hunmuk.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunmuk.api.domain.User;
import com.hunmuk.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error")
                .requestMatchers(toH2Console());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                return http

                        .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/").permitAll()  // / 경로 인증 필요
                                .requestMatchers("/auth/login").permitAll()  // /admin 경로 인증 필요
                                .requestMatchers("/auth/loginProc").permitAll()  // /admin 경로 인증 필요
                                .requestMatchers("/auth/signup").permitAll()  // /admin 경로 인증 필요
                                .requestMatchers("/user").hasRole("USER")  // /user 경로 USER 권한 필요
                                .requestMatchers("/admin").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))
                                .anyRequest().authenticated()
                        )
                        .formLogin((form) -> form
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/loginProc")
                                .defaultSuccessUrl("/")
                                .permitAll()
                        )
                        //.userDetailsService(userDetailsService(userRepository))
                        .rememberMe((rememberMe) -> rememberMe
                                .key("remember-me")
                                .tokenValiditySeconds(86400*30)
                                .alwaysRemember(false)
                        )
                        .csrf(AbstractHttpConfigurer::disable)
                        .build();

    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
   /*     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername("hunmuk")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();
        manager.createUser(user);*/

        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            return new UserPrincipal(user, user.getId());
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(
                16,
                8,
                1,
                32,
                64);
    }
}
