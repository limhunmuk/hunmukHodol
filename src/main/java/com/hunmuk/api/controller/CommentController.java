package com.hunmuk.api.controller;

import com.hunmuk.api.request.comment.CommentCreate;
import com.hunmuk.api.request.comment.CommentDelete;
import com.hunmuk.api.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public void write(@PathVariable Long postId, @RequestBody @Valid CommentCreate request) {

        log.info("코멘트 저장 시작 ===============");
        commentService.write(postId, request);
        log.info("코멘트 저장 완료 ===============");
    }

    @PostMapping("/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId, @RequestBody @Valid CommentDelete request) {

        log.info("코멘트 삭제 시작 ===============");
        commentService.delete(commentId, request);
        log.info("코멘트 삭제 완료 ===============");
    }
}
