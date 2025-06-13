package com.example.QLDA_Project.controller;

import com.example.QLDA_Project.model.TinTuyenDung;
import com.example.QLDA_Project.service.TinTuyenDungService;
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
@RequestMapping("/jobs")
public class PublicJobController {

    @Autowired
    private TinTuyenDungService tinTuyenDungService;

    @GetMapping
    public String listJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayDang").descending());
        Page<TinTuyenDung> jobsPage;
        
        if (keyword != null && !keyword.isEmpty() || location != null && !location.isEmpty()) {
            // TODO: Implement search functionality in service layer
            jobsPage = tinTuyenDungService.searchJobs(keyword, location, pageable);
        } else {
            jobsPage = tinTuyenDungService.getAllActiveJobsPaginated(pageable);
        }

        model.addAttribute("jobs", jobsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobsPage.getTotalPages());
        model.addAttribute("totalItems", jobsPage.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("location", location);
        
        return "jobs";
    }

    @GetMapping("/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        TinTuyenDung job = tinTuyenDungService.getJobById(id);
        model.addAttribute("job", job);
        return "job-details";
    }
} 