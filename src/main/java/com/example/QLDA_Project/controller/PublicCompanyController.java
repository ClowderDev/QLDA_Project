package com.example.QLDA_Project.controller;

import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.service.CongTyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/companies")
public class PublicCompanyController {

    @Autowired
    private CongTyService congTyService;

    @GetMapping
    public String listCompanies(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("tenCongTy").ascending());
        Page<CongTy> companiesPage;

        if ((keyword != null && !keyword.trim().isEmpty()) || (location != null && !location.trim().isEmpty())) {
            companiesPage = congTyService.searchCompanies(keyword, location, pageable);
        } else {
            companiesPage = congTyService.getAllCompanies(pageable);
        }

        model.addAttribute("companies", companiesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", companiesPage.getTotalPages());
        model.addAttribute("totalItems", companiesPage.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("location", location);

        return "companies";
    }

    @GetMapping("/{id}")
    public String viewCompany(@PathVariable Long id, Model model) {
        return congTyService.getCompanyById(id)
                .map(company -> {
                    model.addAttribute("company", company);
                    return "company-details";
                })
                .orElse("redirect:/companies");
    }
} 