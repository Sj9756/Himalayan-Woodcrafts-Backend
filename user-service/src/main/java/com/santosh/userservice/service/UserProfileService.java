package com.santosh.userservice.service;

import com.santosh.userservice.dto.UpdateProfileRequest;
import com.santosh.userservice.dto.UserProfileResponse;
import com.santosh.userservice.exception.FileUploadException;
import com.santosh.userservice.exception.ResourceNotFoundException;
import com.santosh.userservice.model.UserProfile;
import com.santosh.userservice.repo.UserProfileRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional
    public UserProfileResponse provisionOrFetchProfile(Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return userProfileRepository.findByKeycloakId(keycloakId)
                .map(UserProfileResponse::from)
                .orElseGet(() -> {
                    UserProfile profile = UserProfile.builder()
                            .keycloakId(keycloakId)
                            .email(jwt.getClaimAsString("email"))
                            .firstName(jwt.getClaimAsString("given_name").trim())
                            .lastName(jwt.getClaimAsString("family_name").trim())
                            .build();
                    UserProfile saved = userProfileRepository.save(profile);
                    return UserProfileResponse.from(saved);
                });
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(String keycloakId) {
        return userProfileRepository.findByKeycloakId(keycloakId)
                .map(UserProfileResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));

    }

    @Transactional
    public UserProfileResponse updateProfile(String keycloakId, UpdateProfileRequest req) {
        UserProfile profile = userProfileRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));

        profile.setFirstName(req.getFirstName().trim());
        profile.setLastName(req.getLastName().trim());
        if (req.getPhone() != null && !req.getPhone().isBlank()) {
            profile.setPhone(req.getPhone());
        }
        if (req.getProfileImageUrl() != null && !req.getProfileImageUrl().isBlank()) {
            profile.setProfileImageUrl(req.getProfileImageUrl());
        }
        UserProfile updated = userProfileRepository.save(profile);
        return UserProfileResponse.from(updated);
    }

    @Transactional
    public UserProfileResponse updateProfileImage(Jwt jwt , MultipartFile image){
        try {
            final List<String> ALLOWED_TYPES = List.of(
                    "image/jpeg",
                    "image/jpg",
                    "image/png",
                    "image/webp"
            );
            String contentType = image.getContentType();

            if (contentType == null ||
                    !ALLOWED_TYPES.contains(contentType)) {
                throw new IllegalArgumentException(
                        "Only JPG, JPEG, PNG and WEBP images are allowed"
                );
            }

            String fileName = UUID.randomUUID()
                    + "_" + image.getOriginalFilename();

            Path uploadPath = Paths.get(
                    "C:\\Users\\sj975\\Downloads\\Himalayan Woodcrafts Backend\\user-service\\uploads\\profile-images"
            );

            Files.createDirectories(uploadPath);

            Files.copy(
                    image.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            String imageUrl =
                    "http://localhost:8081/uploads/profile-images/" + fileName;

            String keycloakId = jwt.getSubject();
            UserProfile user = userProfileRepository
                    .findByKeycloakId(keycloakId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User not found"));
            user.setProfileImageUrl(imageUrl);
            UserProfile userProfile = userProfileRepository.save(user);
            return UserProfileResponse.from(userProfile);

        }
        catch (IOException e){
            throw new FileUploadException("Failed to upload image");
        }
    }
}
