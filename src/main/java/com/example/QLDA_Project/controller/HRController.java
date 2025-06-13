package com.example.QLDA_Project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.example.QLDA_Project.dto.UserProfileDto;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.lang.ProcessBuilder.Redirect;
import java.net.URI;
import java.util.Map;

@Controller
@RequestMapping("/hr")
@RequiredArgsConstructor
public class HRController {

    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_HR_STAFF', 'ROLE_RECRUITER', 'ROLE_CV_STAFF')")
    public String profilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("activeMenu", "profile");
        return "hr/profilePage";
    }

    @GetMapping("/api/profile")
    @PreAuthorize("hasAnyRole('ROLE_HR_STAFF', 'ROLE_RECRUITER', 'ROLE_CV_STAFF')")
    @ResponseBody
    public ResponseEntity<?> getHRProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/api/profile", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_HR_STAFF', 'ROLE_RECRUITER', 'ROLE_CV_STAFF')")
    @ResponseBody
    public ResponseEntity<?> updateHRProfile(@Valid @ModelAttribute UserProfileDto profileDto, HttpServletRequest request) {
        try {
            if (profileDto == null) {
                return ResponseEntity.badRequest().body("No profile data provided or invalid format.");
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(auth.getName());
            
            User updatedUser = userService.updateUserProfile(user.getId(), profileDto);
            
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                return ResponseEntity.ok(updatedUser);
            }
            
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/hr/profile"))
                .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while updating the profile");
        }
    }

    @PutMapping("/api/profile/password")
    @PreAuthorize("hasAnyRole('ROLE_HR_STAFF', 'ROLE_RECRUITER', 'ROLE_CV_STAFF')")
    @ResponseBody
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(auth.getName());
            userService.changePassword(user.getId(), request.currentPassword(), request.newPassword());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while changing the password");
        }
    }

    private record PasswordChangeRequest(String currentPassword, String newPassword) {}
} 