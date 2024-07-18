package com.hunmuk.api.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation;

    public void addValidation(String fieldName, String filedMessage) {
        this.validation.put(fieldName, filedMessage);
    }

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }
}
