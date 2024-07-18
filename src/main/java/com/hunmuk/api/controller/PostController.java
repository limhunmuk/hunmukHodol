package com.hunmuk.api.controller;

import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.request.PostEdit;
import com.hunmuk.api.request.PostSearch;
import com.hunmuk.api.response.PostResponse;
import com.hunmuk.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // http method
    // get, post, put, patch, delete, option, head, trace, connect

    @PostMapping("/posts/v1")
    public String getPostsV1(@RequestParam String title, @RequestParam String contents) {
        log.info("title > {}", title);
        log.info("contents > {}", contents);

        return "hello posts";
    }

    @PostMapping("/posts/v2")
    public String getPostsV2(@RequestParam Map<String, String> params) {
        log.info("params > {}", params);

        return "hello posts";
    }

    @PostMapping("/posts/v3")
    public String getPostsV3(PostCreate params) {
        log.info("params > {}", params);

        return "hello posts";
    }

    /**
     * 등록
     * @param request
     */
    @PostMapping("/posts")
    public void getPostsRequest(@RequestBody @Valid PostCreate request) {

        request.validate();
        log.info("params > {}", request);
        postService.write(request);
    }

    /**
     * 조회
     * @param postId
     * @return
     */
    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        log.info("params > {}", postId);

        return postService.get(postId);
    }

    /**
     * 목록
     * @param postSearch
     * @return
     */
    @GetMapping("/posts")
    public List<PostResponse> getPosts(@ModelAttribute PostSearch postSearch) {

        return  postService.getList(postSearch);
    }

    /**
     * 수정
     * @param postId
     * @param edit
     */
    @PatchMapping("/posts/{postId}")
    public void setPost(@PathVariable Long postId, @RequestBody @Valid PostEdit edit) {
        log.info("params > {}", postId);
        postService.edit(postId, edit);
    }

    /**
     * 삭제
     * @param postId
     */
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        log.info("params > {}", postId);

        postService.delete(postId);
    }


}
