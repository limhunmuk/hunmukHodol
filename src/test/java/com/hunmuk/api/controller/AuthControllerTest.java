package com.hunmuk.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunmuk.api.domain.Session;
import com.hunmuk.api.domain.User;
import com.hunmuk.api.repository.SessionRepository;
import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.request.Login;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void clean() {
        userRepository.deleteAll();

    }

    @Test
    @DisplayName("로그인 성공 ")
    void testCase1() throws Exception {

        //given
        userRepository.save(User.builder()
                .email("admin@naver.com")
                .password("admin")
                .build());

        Login login = Login.builder()
                .email("admin@naver.com")
                .password("admin")
                .build();

        //expected
        String json  = objectMapper.writeValueAsString(login);
        System.out.println("json = " + json);

        // test 에서는 init db를 생성하지않는다.
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                //.andExpect(content().string(""))
                .andDo(print());

    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개 생성")
    void testCase2() throws Exception {

        //given
        User loginUser = userRepository.save(User.builder()
                .email("admin@naver.com")
                .password("admin")
                .build());

        Login login = Login.builder()
                .email("admin@naver.com")
                .password("admin")
                .build();

        //expected
        String json  = objectMapper.writeValueAsString(login);
        System.out.println("json = " + json);

        // test 에서는 init db를 생성하지않는다.
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                //.andExpect(content().string(""))
                .andDo(print());

       // User loggedInUser = userRepository.findById(loginUser.getId()).orElseThrow(RuntimeException::new);
       // Assertions.assertEquals(1L, loggedInUser.getSessions().stream().count());
        Assertions.assertEquals(1, sessionRepository.count());

    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 응답")
    void testCase3() throws Exception {

        //given
        User loginUser = userRepository.save(User.builder()
                .email("admin@naver.com")
                .password("admin")
                .build());

        Login login = Login.builder()
                .email("admin@naver.com")
                .password("admin")
                .build();

        //expected
        String json  = objectMapper.writeValueAsString(login);
        System.out.println("json = " + json);

        // test 에서는 init db를 생성하지않는다.
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
                //.andExpect(content().string(""))
                .andDo(print());  // todo test list

       // User loggedInUser = userRepository.findById(loginUser.getId()).orElseThrow(RuntimeException::new);
       // Assertions.assertEquals(1L, loggedInUser.getSessions().stream().count());
        Assertions.assertEquals(1, sessionRepository.count());

    }
    @Test
    @Transactional
    @DisplayName("로그인 후 권한이 필요한 페이지에 접속한다")
    void testCase4() throws Exception {

        //given
        User user = User.builder()
                .name("lhm")
                .email("admin@naver.com")
                .password("admin")
                .build();

        Session session = user.addSession();

        userRepository.save(user);

        Login login = Login.builder()
                .email("admin@naver.com")
                .password("admin")
                .build();

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                        .header("Authorization" , session.getAccessToken())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());  // todo test list


    }
    @Test
    @DisplayName("로그인 후 검증이 되지 않은 세션으로 권한이 필요한 페이지에 접근할수 없다")
    void testCase5() throws Exception {

        //given
        User user = User.builder()
                .name("lhm")
                .email("admin@naver.com")
                .password("admin")
                .build();

        Session session = user.addSession();

        userRepository.save(user);

        Login login = Login.builder()
                .email("admin@naver.com")
                .password("admin")
                .build();

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                        .header("Authorization" , session.getAccessToken() + "_other")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized())
                .andDo(print());  // todo test list


    }

}