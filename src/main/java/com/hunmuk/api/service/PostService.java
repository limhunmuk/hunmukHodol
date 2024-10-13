package com.hunmuk.api.service;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.domain.PostEditor;
import com.hunmuk.api.domain.User;
import com.hunmuk.api.exception.PostNotFound;
import com.hunmuk.api.exception.UserNotFound;
import com.hunmuk.api.repository.post.PostRepository;
import com.hunmuk.api.repository.UserRepository;
import com.hunmuk.api.request.post.PostCreate;
import com.hunmuk.api.request.post.PostEdit;
import com.hunmuk.api.request.post.PostSearch;
import com.hunmuk.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void write(Long userId, PostCreate postCreate) {
        log.info("createPost");

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        //postCreate
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .contents(postCreate.getContents())
                .user(user)
                .build();

        System.out.println(" 훈묵 메인에서" );
        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);
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

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        //post.change(postEdit.getTitle(), postEdit.getContents());
     //   postRepository.save(post);
        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .contents(postEdit.getContents())
                .build();

      /*  if(postEdit.getTitle() != null){
            editorBuilder.title(postEdit.getTitle());
        }

        if(postEdit.getContents() != null){
            editorBuilder.contents(postEdit.getContents());
        }*/

        post.edit(postEditor);

        return new PostResponse(post);


    }

    public void delete(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
