package com.hunmuk.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class HunMukException extends RuntimeException{

    private final Map<String, String> validation = new HashMap<>();

    public HunMukException(String message) {
        super(message);
    }

    public HunMukException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String filedMessage) {
        this.validation.put(fieldName, filedMessage);
    }
}
