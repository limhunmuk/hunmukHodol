package com.hunmuk.api.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunmuk.api.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class Http401Handler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
      //log.error("Authentication Entry Point");
        log.error("[인증오류 발생] : {}", authException.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("401")
                .message("인증이 필요합니다.")
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 200에서 400으로 변경
        objectMapper.writeValue(response.getWriter(), errorResponse);

    }
}
