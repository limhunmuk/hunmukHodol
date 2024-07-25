package com.hunmuk.api.service;

import com.hunmuk.api.domain.Post;
import com.hunmuk.api.domain.PostEditor;
import com.hunmuk.api.exception.PostNotFound;
import com.hunmuk.api.repository.PostRepository;
import com.hunmuk.api.request.PostCreate;
import com.hunmuk.api.request.PostEdit;
import com.hunmuk.api.request.PostSearch;
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

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        log.info("createPost");

        //postCreate
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .contents(postCreate.getContents())
                .build();

        System.out.println("\"test\" = " + "test new");
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
