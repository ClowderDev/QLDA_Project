package com.example.QLDA_Project.controller;

import com.example.QLDA_Project.model.TinTuyenDung;
import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.service.TinTuyenDungService;
import com.example.QLDA_Project.service.CongTyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private TinTuyenDungService tinTuyenDungService;

    @Autowired
    private CongTyService congTyService;

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("username", auth.getName());
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        
        // Get recent jobs
        Pageable recentJobsPageable = PageRequest.of(0, 6); // Get 6 most recent jobs
        List<TinTuyenDung> recentJobs = tinTuyenDungService.getAllActiveJobsPaginated(recentJobsPageable).getContent();
        model.addAttribute("recentJobs", recentJobs);
        
        // Get featured companies
        Pageable featuredCompaniesPageable = PageRequest.of(0, 3); // Get 3 featured companies
        List<CongTy> featuredCompanies = congTyService.getFeaturedCompanies(featuredCompaniesPageable);
        model.addAttribute("featuredCompanies", featuredCompanies);
        
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                switch (role) {
                    case "ROLE_ADMIN":
                        return "redirect:/admin/index";
                    case "ROLE_HR_STAFF":
                        return "redirect:/hr/dashboard";
                    case "ROLE_CANDIDATE":
                        return "redirect:/user/dashboard";
                    case "ROLE_RECRUITER":
                    case "ROLE_CV_STAFF":
                        return "redirect:/hr/dashboard";
                }
            }
        }
        return "redirect:/";
    }    
    @GetMapping("/admin/index")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminIndex() {
        return "admin/index";
    }
    
    @GetMapping("/hr/dashboard")
    @PreAuthorize("hasRole('ROLE_HR_STAFF')")
    public String hrDashboard() {
        return "hr/dashboard";
    }

    @GetMapping("/user/dashboard")
    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    public String userDashboard() {
        return "user/dashboard";
    }

}