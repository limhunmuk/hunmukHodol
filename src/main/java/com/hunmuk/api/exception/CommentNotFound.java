package com.hunmuk.api.exception;

public class CommentNotFound extends HunMukException{

    private static final String MESSAGE = "댓글을 찾을 수 없습니다.";

    public CommentNotFound() {
        super(MESSAGE);
    }

    public CommentNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode(){
        return 404;
    }
}
