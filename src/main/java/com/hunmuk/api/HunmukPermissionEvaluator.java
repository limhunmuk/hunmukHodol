package com.hunmuk.api;

import com.hunmuk.api.config.UserPrincipal;
import com.hunmuk.api.exception.PostNotFound;
import com.hunmuk.api.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
public class HunmukPermissionEvaluator implements PermissionEvaluator {

    private final PostRepository postRepository;

    @Override
    public boolean hasPermission(org.springframework.security.core.Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(org.springframework.security.core.Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        var post = postRepository.findById(principal.getUserId()).orElseThrow(PostNotFound::new);

        if(!post.getUserId().equals(principal.getUserId())){
            log.info("[인증실패] 작성권한이 없습니다. targetId: {}, targetType: {}, permission: {}", targetId, targetType, permission);
            return false;
        }

        return true;
    }
}
