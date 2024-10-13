package com.hunmuk.api.exception;

import lombok.Getter;

@Getter
public class InvaildPassword extends HunMukException{

    private static final String MESSAGE = "잘못된 패스워드입니다.";

    public InvaildPassword() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode(){
        return 400;
    }

}
