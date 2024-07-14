package com.hunmuk.api.controller;

import com.hunmuk.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        //if(e.hasErrors()) {
            log.info("hasErrors > {}", e.hasErrors());

        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된요청입니다.")
                .build();

        for(FieldError fieldError : e.getFieldErrors()) {
                log.info("fieldError > {}", fieldError);
                log.info("field > {}", fieldError.getField());
                log.info("defaultMessage > {}", fieldError.getDefaultMessage());
                response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
        //}
        /**
        //.. d.MethodArgumentNotValidException
        //System.out.println(" exception advise !!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
        log.info("exception advise !!!!!!!!!!!!!!!!!!!!!!!!!!!!!", e);

        FieldError fieldError = e.getFieldError();
        log.info("fieldError > {}", fieldError);

        String field = fieldError.getField();
        log.info("field > {}", field);
        String defaultMessage = fieldError.getDefaultMessage();
        log.info("defaultMessage > {}", defaultMessage);

        Map<String, String> error = Map.of(field, defaultMessage);
        log.info("error > {}", error);

        return error;
         */
    }
}
