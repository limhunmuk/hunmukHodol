package com.hunmuk.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunmuk.api.config.HunmukMockUser;
import com.hunmuk.api.domain.Comment;
import com.hunmuk.api.domain.Post;
import com.hunmuk.api.domain.User;
import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.repository.comment.CommentRepository;
import com.hunmuk.api.repository.post.PostRepository;
import com.hunmuk.api.request.comment.CommentCreate;
import com.hunmuk.api.request.comment.CommentDelete;
import com.hunmuk.api.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    //@BeforeEach
    @AfterEach
    void clean() {
        commentRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 작성")
    @HunmukMockUser
    void testCase1() throws Exception {

        //given
      User user = User.builder()
                .email("ihm2119@naver.com")
                .password("")
                .name("훈묵쓰")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("제목이유.")
                .contents("내용이유.")
                .user(user)
                .build();

        postRepository.save(post);
        CommentCreate comment = CommentCreate.builder()
                .author("훈묵쓰")
                .password("1234")
                .content("댓글내용")
                .build();

        //commentRepository.save(comment);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(comment);
        System.out.println("json = " + json);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/{postId}/comments", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(commentRepository.findAll().size()).isEqualTo(1);
        Comment comment1 = commentRepository.findAll().get(0);
        assertThat(comment1.getAuthor()).isEqualTo("훈묵쓰");
        assertThat(comment1.getContent()).isEqualTo("댓글내용");
        assertThat(comment1.getPassword()).isEqualTo("1234");

    }

    @Test
    @HunmukMockUser
    @DisplayName("댓글 삭제")
    void testCase2() throws Exception {

        //given
        User user = User.builder()
                .email("ihm2119@naver.com")
                .password("")
                .name("훈묵쓰")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("제목이유.")
                .contents("내용이유.")
                .user(user)
                .build();

        postRepository.save(post);
        String password = "1234";
        String encodedPwd = passwordEncoder.encode(password);
        Comment comment = Comment.builder()
                .author("훈묵쓰")
                .password(encodedPwd)
                .content("댓글내용")
                .build();

        commentRepository.save(comment);

        CommentDelete commentDelete = new CommentDelete("1234");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDelete = objectMapper.writeValueAsString(commentDelete);
        System.out.println("json = " + jsonDelete);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments/{commentId}/delete", comment.getId())
                        .contentType(APPLICATION_JSON)
                        .content(jsonDelete)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }




}