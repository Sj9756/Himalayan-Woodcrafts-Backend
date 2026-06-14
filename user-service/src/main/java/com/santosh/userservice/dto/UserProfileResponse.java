package com.santosh.userservice.dto;

import com.santosh.userservice.model.UserProfile;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String keycloakId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private String profileImageUrl;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserProfileResponse from(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .keycloakId(profile.getKeycloakId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .fullName(profile.getFullName())
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .profileImageUrl(profile.getProfileImageUrl())
                .status(profile.getStatus().name())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
