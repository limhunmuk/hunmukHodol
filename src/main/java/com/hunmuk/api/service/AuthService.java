package com.hunmuk.api.service;

import com.hunmuk.api.domain.Session;
import com.hunmuk.api.domain.User;
import com.hunmuk.api.exception.InvaildSignInfomation;
import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn(Login request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(InvaildSignInfomation::new);

        Session session = user.addSession();
        System.out.println("user 로그인 = 세션 생성완료 !!! " + user);
        return user.getId();
    }
}
