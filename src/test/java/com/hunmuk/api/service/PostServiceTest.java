package com.hunmuk.api.service;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.repository.PostRepository;
import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.request.PostSearch;
import com.hunmuk.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(postRepository.count()).isEqualTo(1);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("글제목");
        assertThat(post.getContents()).isEqualTo("글내용");
    }

    //@Test
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
        assertThat(post.getId()).isEqualTo(id);
        PostResponse asserionPost = postService.get(1L);
        assertThat(asserionPost.getTitle()).isEqualTo("글제목");
        assertThat(asserionPost.getContents()).isEqualTo("글내용");

    }
    @Test
    @DisplayName("글 1 페이지 조회")
    void 글_여러개조회(){

        //Pageable pageable = PageRequest.of(0, 10);

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("글제목 " + i)
                        .contents("글내용 " + i)
                        .build())
                .toList();

        postRepository.saveAll(requestPosts);

        PostSearch search = PostSearch.builder()
                .page(1)
                .build();

        List<PostResponse> posts = postService.getList(search);


        assertThat(10L).isEqualTo(posts.size());
        assertThat(posts.get(0).getTitle()).isEqualTo("글제목 19");
        assertThat(posts.get(0).getContents()).isEqualTo("글내용 19");

        System.out.println("posts = " + posts);


    }



}