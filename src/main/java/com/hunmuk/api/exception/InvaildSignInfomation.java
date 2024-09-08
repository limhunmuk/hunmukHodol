package com.hunmuk.api.exception;

import lombok.Getter;

@Getter
public class InvaildSignInfomation extends HunMukException{

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";
    public InvaildSignInfomation() {
        super(MESSAGE);
    }


    @Override
    public int getStatusCode(){
        return 400;
    }

}
