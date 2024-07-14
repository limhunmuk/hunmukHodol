package com.hunmuk.api.service;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.repository.PostRepository;
import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.request.PostSearch;
import com.hunmuk.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        log.info("createPost");

        //postCreate
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .contents(postCreate.getContents())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .build();

    }

    public List<PostResponse> getList(PostSearch search) {
        // Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("id").descending());
        return postRepository.getList(search).stream()
                .map(PostResponse::new)
                .toList();
    }
}
