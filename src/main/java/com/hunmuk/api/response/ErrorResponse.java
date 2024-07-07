package com.hunmuk.api.response;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();


    public void addValidation(String fieldName, String filedMessage) {
        this.validation.put(fieldName, filedMessage);
    }

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
