package com.santosh.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {
    @GetMapping("/public/hello")
    public String publicApi() {
        return "Public API – no token required demo";
    }

    @GetMapping("/user/hello")
    public String userApi() {
        return "Hello USER";
    }

    @GetMapping("/admin/hello")
    public String adminApi() {
        return "Hello ADMIN you are premium user";
    }
}
