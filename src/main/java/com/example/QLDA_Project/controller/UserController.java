package com.example.QLDA_Project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.QLDA_Project.dto.UserProfileDto;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        model.addAttribute("user", user);
        return "user/profilePage";
    }

    @GetMapping("/api/profile")
    @ResponseBody
    public ResponseEntity<?> getUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/profile")
    @ResponseBody
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UserProfileDto profileDto, HttpServletRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByUsername(auth.getName());
            User updatedUser = userService.updateUserProfile(user.getId(), profileDto);
            
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                return ResponseEntity.ok(updatedUser);
            }
            
            return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body("redirect:/user/profile");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while updating the profile");
        }
    }

    @PutMapping("/api/profile/password")
    @ResponseBody
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        userService.changePassword(user.getId(), request.currentPassword(), request.newPassword());
        return ResponseEntity.ok().build();
    }

    private record PasswordChangeRequest(String currentPassword, String newPassword) {}
}
