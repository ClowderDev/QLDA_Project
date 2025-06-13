package com.example.QLDA_Project.service;

import com.example.QLDA_Project.dto.UserProfileDto;
import com.example.QLDA_Project.dto.UserRegistrationDto;
import com.example.QLDA_Project.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User registerUser(UserRegistrationDto registrationDto);
    User updateUserProfile(Long userId, UserProfileDto profileDto);
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void deleteUser(Long id);
    void changePassword(Long userId, String currentPassword, String newPassword);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User getCurrentUser(Authentication authentication);
    User getCurrentUserWithCompanyDetails(Authentication authentication);
} 