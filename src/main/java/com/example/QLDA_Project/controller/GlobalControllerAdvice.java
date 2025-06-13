package com.example.QLDA_Project.controller;

import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final UserService userService;

    @ModelAttribute
    public void addCurrentUserToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            try {
                User user = userService.getUserByUsername(auth.getName());
                model.addAttribute("user", user);
                model.addAttribute("isAuthenticated", true);
            } catch (Exception e) {
              
                model.addAttribute("user", null);
                model.addAttribute("isAuthenticated", false);
            }
        } else {
            model.addAttribute("user", null);
            model.addAttribute("isAuthenticated", false);
        }
    }
}
