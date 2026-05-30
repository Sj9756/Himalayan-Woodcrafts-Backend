package com.santosh.userservice.controller;

import com.santosh.userservice.service.UserProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserProfileService service;

    public UserController(UserProfileService service) {
        this.service = service;
    }


//    @GetMapping("/user/me")
//    public String me() {
//        return "working";
//    }
    @GetMapping("/user/me")
    public Map<String, Object> me(Authentication authentication) {

        Jwt jwt = (Jwt) authentication.getPrincipal();

        return jwt.getClaims();
    }

//    @GetMapping("/user/me")
//    public Map<String, Object> me(
//            Jwt jwt) {
//
//        return jwt.getClaims();
//    }

}
