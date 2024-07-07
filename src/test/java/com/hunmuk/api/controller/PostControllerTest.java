package com.hunmuk.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunmuk.api.domain.Post;
import com.hunmuk.api.repository.PostRepository;
import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    //@Test
    @DisplayName("포스트를 요청을 날려본다")
    void getPostsV1() throws Exception {

         mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                         .contentType(APPLICATION_FORM_URLENCODED)
                         .param("title", "글제목")
                         .param("content", "글내용")
                 )
                .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.content().string("hello posts"))
                 .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("포스트를 요청을 날려본다")
    void getPostsV2() throws Exception {

        PostCreate request = PostCreate.builder()
                .title("제목")
                .contents("내용")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writeValueAsString(request);
        System.out.println("json = " + json);

         mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                         .contentType(APPLICATION_JSON)
                         .content(json)
                 )
                .andExpect(status().isOk())
                 .andExpect(content().string("{}"))
                 .andDo(MockMvcResultHandlers.print());

    }

   @Test
    @DisplayName("포스트를 요청 시 title 필수다")
    void getPosts() throws Exception {

       PostCreate request = PostCreate.builder()
               .contents("내용입니다")
               .build();

       ObjectMapper objectMapper = new ObjectMapper();
       String json  = objectMapper.writeValueAsString(request);
       System.out.println("json = " + json);


       mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                         .contentType(APPLICATION_JSON)
                         .content(json)
                 )
                .andExpect(status().isBadRequest())
                //.andExpect(MockMvcResultMatchers.content().string("hello posts"))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title은 필수입니다."))
                 .andExpect(jsonPath("$.code").value("400"))
                 .andExpect(jsonPath("$.message").value("잘못된요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("title은 필수입니다."))
                 .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("포스트를 요청 시 저장한다")
    void save() throws Exception {

        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .contents("내용입니다")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writeValueAsString(request);
        System.out.println("json = " + json);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                         .contentType(APPLICATION_JSON)
                         .content(json)
                 )
                .andExpect(status().isOk())
                 .andExpect(content().string("{}"))
                 .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                         .contentType(APPLICATION_JSON)
                         .content("{\"title\" : \"제목입니다.\"," +
                                 "\"contents\" : \"내용입니다\"}")
                 )
                .andExpect(status().isOk())
                 .andExpect(content().string("{}"))
                 .andDo(MockMvcResultHandlers.print());

         //then
        assertEquals(2L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다", post.getContents());

        List<Post> all = postRepository.findAll();
        System.out.println(">>>>>>>>>>>>>>>>>>> all.size() = " + all.size());
        for (Post post1 : all) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>> post1 = " + post1.toString());
        }

    }

    @Test
    @DisplayName("단건 조회")
    void 글_단건_조회() throws Exception {

        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .contents("내용입니다.")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("제목입니다."))
                .andExpect(jsonPath("$.contents").value("내용입니다."))
                .andDo(MockMvcResultHandlers.print());



    }

}