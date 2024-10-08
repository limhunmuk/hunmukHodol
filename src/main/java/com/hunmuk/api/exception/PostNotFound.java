package com.hunmuk.api.exception;

public class PostNotFound extends HunMukException{

    private static final String MESSAGE = "게시글을 찾을 수 없습니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    public PostNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode(){
        return 404;
    }
}
