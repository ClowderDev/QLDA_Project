package com.example.QLDA_Project.controller;

import com.example.QLDA_Project.dto.UserRegistrationDto;
import com.example.QLDA_Project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthViewController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error,
                              @RequestParam(required = false) String logout,
                              @RequestParam(required = false) String register,
                              Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }
        if (logout != null) {
            model.addAttribute("logout", "You have been logged out successfully");
        }
        if (register != null) {
            model.addAttribute("register", register);
        }
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "login"; 
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserRegistrationDto registrationDto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "Please check your input and try again");
            return "redirect:/login?register=false";
        }

        try {
            userService.registerUser(registrationDto);
            redirectAttributes.addFlashAttribute("register", "true");
            return "redirect:/login?register=true";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("registerError", true);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login?register=false";
        }
    }
} 