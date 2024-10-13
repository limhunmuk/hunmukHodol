package com.hunmuk.api.controller;

import com.hunmuk.api.config.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public String main() {
        return "[출력]>>> 메인페이지 입니다";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return "[출력]>>> 유저페이지 입니다";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "[출력]>>> 어드민페이지 입니다";
    }
}

