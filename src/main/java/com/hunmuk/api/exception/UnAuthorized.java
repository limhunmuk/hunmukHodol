package com.hunmuk.api.exception;

public class UnAuthorized extends HunMukException{

    private static final String MESSAGE = "인증되지 않았습니다.";

    public UnAuthorized() {
        super(MESSAGE);
    }

    public UnAuthorized(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode(){
        return 401;
    }
}
