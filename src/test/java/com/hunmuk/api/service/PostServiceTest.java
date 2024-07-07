package com.hunmuk.api.service;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.repository.PostRepository;
import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.response.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void 글작성(){
        //given
        PostCreate postRequest = PostCreate.builder()
                .title("글제목")
                .contents("글내용")
                .build();

        //when
        postService.write(postRequest);

        //then
        Assertions.assertThat(postRepository.count()).isEqualTo(1);
        Post post = postRepository.findAll().get(0);
        Assertions.assertThat(post.getTitle()).isEqualTo("글제목");
        Assertions.assertThat(post.getContents()).isEqualTo("글내용");
    }

    @Test
    @DisplayName("글 조회 할꺼임")
    void 글조회(){

        PostCreate postRequest = PostCreate.builder()
                .title("글제목")
                .contents("글내용")
                .build();

        postService.write(postRequest);

        //given
        Long id = 1L;

        //when
        PostResponse post = postService.get(id);

        //then
        Assertions.assertThat(post.getId()).isEqualTo(id);
        PostResponse asserionPost = postService.get(1L);
        Assertions.assertThat(asserionPost.getTitle()).isEqualTo("글제목");
        Assertions.assertThat(asserionPost.getContents()).isEqualTo("글내용");

    }


}