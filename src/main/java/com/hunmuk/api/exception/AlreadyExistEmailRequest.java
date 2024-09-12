package com.hunmuk.api.exception;

import lombok.Getter;

@Getter
public class AlreadyExistEmailRequest extends HunMukException{

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";
    public String fieldName;
    public String message;

    public AlreadyExistEmailRequest() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode(){
        return 400;
    }

}
