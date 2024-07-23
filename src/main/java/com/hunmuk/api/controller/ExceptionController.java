package com.hunmuk.api.controller;

import com.hunmuk.api.exception.HunMukException;
import com.hunmuk.api.exception.PostNotFound;
import com.hunmuk.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
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
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFound.class)
    @ResponseBody
    public ErrorResponse postNotFound(PostNotFound e) {

        ErrorResponse response = ErrorResponse.builder()
                .code("404")
                .message(e.getMessage())
                .build();

        return response;
    }


    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(HunMukException.class)
    public ResponseEntity<ErrorResponse> hunmukException(HunMukException e) {

        int statsCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statsCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        // 응답 validation -> title -> 제목에 바보가 포함될수 없습니다
   /*     if (e instanceof InvaildRequest) {

            InvaildRequest invaildRequest = (InvaildRequest) e;
            String fieldName = invaildRequest.getFieldName();
            String message = invaildRequest.getMessage();

            body.addValidation(fieldName, message);
        }*/

        return ResponseEntity.status(statsCode)
                .body(body);

    }
}
