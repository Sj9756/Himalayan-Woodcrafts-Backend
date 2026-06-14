package com.santosh.userservice;

import com.santosh.userservice.exception.ResourceNotFoundException;
import com.santosh.userservice.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    private final UserProfileService userProfileService = new UserProfileService();

}
