package com.hunmuk.api.service;

import com.hunmuk.api.crpto.PasswordEncoder;
import com.hunmuk.api.crpto.ScryptPasswordEncoder;
import com.hunmuk.api.domain.User;
import com.hunmuk.api.exception.AlreadyExistEmailRequest;
import com.hunmuk.api.exception.InvaildSignInfomation;
import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.request.Login;
import com.hunmuk.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long signIn(Login request) {
        //User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword()).orElseThrow(InvaildSignInfomation::new);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvaildSignInfomation::new);

        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvaildSignInfomation();
        }

        //Session session = user.addSession();

        return user.getId();
    }

    public void signup(Signup signup) {

        Optional<User> byEmail = userRepository.findByEmail(signup.getEmail());
        if (byEmail.isPresent()) {
            throw new AlreadyExistEmailRequest();
        }

        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        User user = User.builder()
                .email(signup.getEmail())
                .password(encoder.encrypt(signup.getPassword()))
                //.password(signup.getPassword())
                .name(signup.getName())
                .build();

        userRepository.save(user);
    }
}
