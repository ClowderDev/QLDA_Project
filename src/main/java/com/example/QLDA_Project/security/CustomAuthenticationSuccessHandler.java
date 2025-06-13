package com.example.QLDA_Project.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
          Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectURL = determineTargetUrl(authorities);
        
        response.sendRedirect(redirectURL);
    }

    private String determineTargetUrl(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            
            switch (role) {
                case "ROLE_ADMIN":
                    return "/admin/index";
                case "ROLE_HR_STAFF":
                    return "/hr/dashboard";
                case "ROLE_CANDIDATE":
                    return "/user/dashboard";
                case "ROLE_RECRUITER":
                    return "/hr/dashboard";  
                case "ROLE_CV_STAFF":
                    return "/hr/dashboard"; 
                default:
                    return "/";
            }
        }
        return "/";
    }
}
