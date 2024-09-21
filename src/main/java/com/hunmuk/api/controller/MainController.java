package com.hunmuk.api.controller;

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

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "[출력]>>> 유저페이지 입니다";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "[출력]>>> 어드민페이지 입니다";
    }
}

