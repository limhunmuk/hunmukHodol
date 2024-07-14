package com.hunmuk.api.service;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.exception.PostNotFound;
import com.hunmuk.api.repository.PostRepository;
import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.request.PostEdit;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @DisplayName("글 1 조회 할꺼임")
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

    @Test
    @DisplayName("글 제목수정")
    void 글_수정(){

        //given
        Post post = Post.builder()
                .title("제목이유.")
                .contents("내용이유.")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .contents("나유 수정.") // 수정안할꺼임 -> assertion 처리 삭제
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post editPost = postRepository.findById(post.getId())
                .orElseThrow(PostNotFound::new);

        assertThat(editPost.getTitle()).isEqualTo("제목이유.");
        assertThat(editPost.getContents()).isEqualTo("나유 수정.");


    }

    @Test
    @DisplayName("게시글 삭제")
    void testCase1(){

        //given
        Post post = Post.builder()
                .title("제목이유.")
                .contents("내용이유.")
                .build();

        postRepository.save(post);

        //when
        postService.delete(post.getId());

        //then
        assertThat(postRepository.count()).isEqualTo(0);

    }

    @Test
    @DisplayName("글 1 조회 error 케이스")
    void 글조회에러(){

        //given
        Post post = Post.builder()
                .title("상품문의")
                .contents("얼만가용?")
                .build();

        postRepository.save(post);

        //assertThat(post.getId()).isEqualTo(id);
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        }, "예외처리 아주 잘못됨.");

        //assertThat(illegalArgumentException.getMessage()).isEqualTo("제정신??.");
        //assertThat(postNotFound.getMessage()).isEqualTo("해당 게시글이 없습니다.");
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는글")
    void testCase2(){

        //given
        Post post = Post.builder()
                .title("제목이유.")
                .contents("내용이유.")
                .build();

        postRepository.save(post);

        //expected
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        }, "예외처리 아주 잘못됨.");


    }





}