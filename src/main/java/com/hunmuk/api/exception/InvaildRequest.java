package com.hunmuk.api.exception;

import lombok.Getter;

@Getter
public class InvaildRequest extends HunMukException{

    private static final String MESSAGE = "잘못된 요청입니다.";
    public String fieldName;
    public String message;

    public InvaildRequest() {
        super(MESSAGE);
    }

    public InvaildRequest(String fieldName, String message) {
        super(MESSAGE);
        super.addValidation(fieldName, message);
    }

    public InvaildRequest(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode(){
        return 400;
    }

}
