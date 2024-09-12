package com.hunmuk.api.service;

import com.hunmuk.api.crpto.PasswordEncoder;
import com.hunmuk.api.domain.User;
import com.hunmuk.api.exception.AlreadyExistEmailRequest;
import com.hunmuk.api.exception.InvaildSignInfomation;
import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.request.Login;
import com.hunmuk.api.request.Signup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void 회원가입(){
        //given

        Signup signup = Signup.builder()
                .email("ihm2119@naver.com")
                .password("1234")
                .name("훈묵쓰")
                .build();

        //when
        authService.signup(signup);

        //then
        Assertions.assertThat(userRepository.count()).isEqualTo(1);
        User next = userRepository.findAll().iterator().next();
        Assertions.assertThat(next.getEmail()).isEqualTo("ihm2119@naver.com");
        Assertions.assertThat(next.getPassword()).isNotEmpty();
        Assertions.assertThat(next.getPassword()).isNotEqualTo("1234");
        Assertions.assertThat(next.getName()).isEqualTo("훈묵쓰");
    }

    @Test
    @DisplayName("중복 회원가입")
    void 회원가입2(){

        //given
        Signup user = Signup.builder()
                .email("ihm1234@naver.com")
                .password("12345")
                .name("훈묵쓰")
                .build();

        authService.signup(user);

        Signup user2 = Signup.builder()
                .email("ihm1234@naver.com")
                .password("12345")
                .name("훈묵쓰")
                .build();
        org.junit.jupiter.api.Assertions.assertThrows(AlreadyExistEmailRequest.class, () -> {
            authService.signup(user2);
        });
    }
    @Test
    @DisplayName("로그인 성공")
    void 로그인(){

        //given
        String encrypt = encoder.encrypt("12345");
        Signup user = Signup.builder()
                .email("ihm1234@naver.com")
                .password(encrypt)
                .name("훈묵쓰")
                .build();

        authService.signup(user);

        //when
        Login login = Login.builder()
                .email("ihm1234@naver.com")
                .password(encrypt)
                .build();

        Long signIn = authService.signIn(login);

        //then
        Assertions.assertThat(signIn).isGreaterThan(0);
    }

    @Test
    @DisplayName("로그인 실패")
    void 로그인_실패(){

        String encrypt = encoder.encrypt("12345");
        String encryptFail = encoder.encrypt("2345");

        //given
        Signup user = Signup.builder()
                .email("ihm1234@naver.com")
                .password(encrypt)
                .name("훈묵쓰")
                .build();

        authService.signup(user);

        //when
        Login login = Login.builder()
                .email("ihm1234@naver.com")
                .password(encryptFail)
                .build();

        //then
        Assertions.assertThatThrownBy(() -> authService.signIn(login))
                .isInstanceOf(InvaildSignInfomation.class);
    }
}