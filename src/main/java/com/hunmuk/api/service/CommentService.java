package com.hunmuk.api.service;

import com.hunmuk.api.domain.Comment;
import com.hunmuk.api.domain.Post;
import com.hunmuk.api.exception.CommentNotFound;
import com.hunmuk.api.exception.InvaildPassword;
import com.hunmuk.api.exception.PostNotFound;
import com.hunmuk.api.repository.comment.CommentRepository;
import com.hunmuk.api.repository.post.PostRepository;
import com.hunmuk.api.request.comment.CommentCreate;
import com.hunmuk.api.request.comment.CommentDelete;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long postId, CommentCreate request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .password(request.getPassword())
                .content(request.getContent())
                .build();

        post.addComment(comment);
    }

    public void delete(Long postId, CommentDelete request) {

        Comment comment = commentRepository.findById(postId)
                .orElseThrow(CommentNotFound::new);

        String encryptPwd = comment.getPassword();
        if (!passwordEncoder.matches(request.getPassword(), encryptPwd)) {
            throw new InvaildPassword();
        }

        commentRepository.delete(comment);
    }
}
