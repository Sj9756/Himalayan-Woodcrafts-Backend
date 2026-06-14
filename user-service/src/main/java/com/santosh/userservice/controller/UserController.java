package com.santosh.userservice.controller;

import com.santosh.userservice.dto.UpdateProfileRequest;
import com.santosh.userservice.dto.UserProfileResponse;
import com.santosh.userservice.model.ApiResponse;
import com.santosh.userservice.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserProfileService userProfileService;

    public UserController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/me/provision")
    public ResponseEntity<ApiResponse<UserProfileResponse>> provision( @AuthenticationPrincipal Jwt jwt) {
        UserProfileResponse profile = userProfileService.provisionOrFetchProfile(jwt);
        return ResponseEntity.ok(ApiResponse.success(profile, HttpStatus.OK, "Profile provisioned successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal Jwt jwt) {
        UserProfileResponse profile = userProfileService.getMyProfile(jwt.getSubject());
        return ResponseEntity.ok(ApiResponse.success(profile, "Profile retrieved successfully"));
    }

        @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @AuthenticationPrincipal Jwt jwt,
            @Validated @RequestBody UpdateProfileRequest request) {
        UserProfileResponse updated = userProfileService.updateProfile(jwt.getSubject(), request);
        return ResponseEntity.ok(ApiResponse.success(updated, "Profile updated successfully"));
    }

    @PostMapping("/me/profile-image")
    public ResponseEntity<ApiResponse<UserProfileResponse>> upload(@AuthenticationPrincipal Jwt jwt, @RequestParam("image") MultipartFile image)  {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.updateProfileImage(jwt,image),"Image Uploaded successfully"));
    }

}
