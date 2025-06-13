package com.example.QLDA_Project.controller;

import com.example.QLDA_Project.dto.UserProfileDto;
import com.example.QLDA_Project.dto.UserRegistrationDto;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.model.CustomUserDetail;
import com.example.QLDA_Project.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        User user = userService.registerUser(registrationDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public void authenticateUser(@Valid @ModelAttribute LoginRequest loginRequest,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws IOException, ServletException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectURL = "/"; 

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMIN":
                    redirectURL = "/admin/index";
                    break;
                case "ROLE_HR_STAFF":
                    redirectURL = "/hr/dashboard";
                    break;
                case "ROLE_CANDIDATE":
                    redirectURL = "/user/dashboard";
                    break;
                case "ROLE_RECRUITER":
                    redirectURL = "/hr/dashboard";
                    break;
                case "ROLE_CV_STAFF":
                    redirectURL = "/hr/dashboard"; 
                    break;
                default:
                    redirectURL = "/";
                    break;
            }
            if (!redirectURL.equals("/")) { 
                break;
            }
        }

        response.sendRedirect(redirectURL);
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body(Map.of("status", "success"));
    }



    private record LoginRequest(String username, String password) {}

} 