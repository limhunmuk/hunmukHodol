package com.hunmuk.api.config;

import com.hunmuk.api.domain.User;
import com.hunmuk.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

import static com.hunmuk.api.domain.User.builder;

@RequiredArgsConstructor
public class HunmukMockUserSecurityContextFactory implements WithSecurityContextFactory<HunmukMockUser> {

    private final UserRepository userRepository;
    @Override
    public SecurityContext createSecurityContext(HunmukMockUser hunmukMockUser) {
        User user = builder()
                .email(hunmukMockUser.email())
                .password(hunmukMockUser.password())
                .name(hunmukMockUser.name())
                .build();

        userRepository.save(user);

        var userPrincipal = new UserPrincipal(user);
        var role = new SimpleGrantedAuthority("ROLE_ADMIN");
        var authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of(role));
        var securityContext = org.springframework.security.core.context.SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        return securityContext;

    }

}
