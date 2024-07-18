package com.hunmuk.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunmuk.api.domain.Post;
import com.hunmuk.api.repository.PostRepository;
import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.request.PostEdit;
import com.hunmuk.api.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                 .andDo(print());

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
                 .andExpect(content().string(""))
                 .andDo(print());

    }

    @Test
    @DisplayName("포스트를 요청 시 title 필수다")
    void getPosts() throws Exception {

       PostCreate request = PostCreate.builder()
               .contents("내용입니다")
               .build();

       ObjectMapper objectMapper = new ObjectMapper();
       String json  = objectMapper.writeValueAsString(request);


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
                 .andDo(print());

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
                 .andExpect(content().string(""))
                 .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                         .contentType(APPLICATION_JSON)
                         .content("{\"title\" : \"제목입니다.\"," +
                                 "\"contents\" : \"내용입니다\"}")
                 )
                .andExpect(status().isOk())
                 .andExpect(content().string(""))
                 .andDo(print());

         //then
        assertEquals(2L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다", post.getContents());
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
                .andDo(print());
    }

    @Test
    @DisplayName("여러건 조회")
    void 글_여러_조회() throws Exception {

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("글제목 " + i)
                        .contents("글내용 " + i)
                        .build())
                .toList();

        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].title").value("글제목 19"))
                .andExpect(jsonPath("$[0].contents").value("글내용 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("글제목 수정")
    void 글_제목_수정() throws Exception {

        //given
        Post post = Post.builder()
                .title("제목이유.")
                .contents("내용이유.")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("임씨유 수정.")
                .contents("내용이유 수정.")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        //expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
                         .contentType(APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(postEdit))
                 )
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.length()").value(10))
                //.andExpect(jsonPath("$[0].title").value("임씨유 수정."))
                //.andExpect(jsonPath("$[0].contents").value("내용이유."))
                .andDo(print())
        ;
    }
    @Test
    @DisplayName("글제목 삭제")
    void 글_제목_삭제() throws Exception {

        //given
        Post post = Post.builder()
                .title("제목이유.")
                .contents("내용이유.")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", post.getId())
                         .contentType(APPLICATION_JSON)
                 )
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("존재하지 않는게시물 조회")
    void testCase1() throws Exception {

    //expected
    mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", 1L)
                    .contentType(APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시물 수정")
    void testCase2() throws Exception {

        PostEdit postEdit = PostEdit.builder()
                .title("임씨유 수정.")
                .contents("내용이유 수정.")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        //expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", 1L)
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(postEdit))
            )
            .andExpect(status().isNotFound())
            .andDo(print());

    }

    @Test
    @DisplayName(" 제목에 바보라는 단어는 금칙어 !!")
    void testCase4() throws Exception {

        PostCreate request = PostCreate.builder()
                .title("바보입니다.")
                .contents("임바보")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());



    }


}