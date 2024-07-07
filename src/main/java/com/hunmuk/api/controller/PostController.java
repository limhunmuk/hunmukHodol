package com.hunmuk.api.controller;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.response.PostResponse;
import com.hunmuk.api.service.PostService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @PostMapping("/posts")
    public void getPostsRequest(@RequestBody @Valid PostCreate request) {
        log.info("params > {}", request);

        postService.write(request);
        //return Map.of();
    }

    @GetMapping("/posts/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        log.info("params > {}", id);

        return postService.get(id);
    }


}
