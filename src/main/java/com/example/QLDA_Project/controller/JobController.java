package com.example.QLDA_Project.controller;

import com.example.QLDA_Project.dto.JobPostingDto;
import com.example.QLDA_Project.model.TinTuyenDung;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.service.TinTuyenDungService;
import com.example.QLDA_Project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hr/jobs")
@PreAuthorize("hasAnyRole('HR_STAFF', 'RECRUITER', 'CV_STAFF')")
public class JobController {

    @Autowired
    private TinTuyenDungService tinTuyenDungService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listJobs(Model model, Authentication authentication, CsrfToken csrfToken) {
        User currentUser = userService.getCurrentUser(authentication);
        List<TinTuyenDung> jobs = tinTuyenDungService.getJobsByCompany(currentUser.getCongTy());
        model.addAttribute("jobs", jobs);
        model.addAttribute("_csrf", csrfToken);
        return "hr/jobs";
    }

    @PostMapping("/create")
    @ResponseBody
    public String createJob(@Valid @RequestBody JobPostingDto jobPostingDto,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        try {
            User hr = userService.getCurrentUser(authentication);
            if (hr.getCongTy() == null) {
                return "error: HR doesn't belong to any company. Please associate the HR user with a company.";
            }
            jobPostingDto.setNgayDang(new Date());
            jobPostingDto.setTrangThai("ACTIVE");
            tinTuyenDungService.createJob(jobPostingDto, hr);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getJob(@PathVariable Long id, Authentication authentication) {
        try {
            User currentUser = userService.getCurrentUser(authentication);
            TinTuyenDung job = tinTuyenDungService.getJobById(id);
            
            if (job == null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Job not found");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }

            if (!job.getCongty().getUsers().contains(currentUser)) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Unauthorized access to job posting");
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }
            
            return new ResponseEntity<>(convertToDto(job), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error fetching job: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/edit")
    @ResponseBody
    public String updateJob(@PathVariable Long id,
                          @Valid @RequestBody JobPostingDto jobPostingDto,
                          Authentication authentication) {
        try {
            User hr = userService.getCurrentUser(authentication);
            if (hr.getCongTy() == null) {
                return "error: HR doesn't belong to any company. Cannot update job.";
            }
            tinTuyenDungService.updateJob(id, jobPostingDto, hr);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @PostMapping("/{id}/delete")
    @ResponseBody
    public String deleteJob(@PathVariable Long id, Authentication authentication) {
        try {
            User hr = userService.getCurrentUser(authentication);
            if (hr.getCongTy() == null) {
                return "error: HR doesn't belong to any company. Cannot delete job.";
            }
            tinTuyenDungService.deleteJob(id, hr);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    private JobPostingDto convertToDto(TinTuyenDung job) {
        JobPostingDto dto = new JobPostingDto();
        dto.setId(job.getId());
        dto.setTieuDe(job.getTieuDe());
        dto.setMoTaCongViec(job.getMoTaCongViec());
        dto.setLinhVuc(job.getLinhVuc());
        dto.setHinhThucLV(job.getHinhThucLV());
        dto.setThanhPhoLV(job.getThanhPhoLV());
        dto.setQuocGiaLV(job.getQuocGiaLV());
        dto.setDiaDiemLV(job.getDiaDiemLV());
        dto.setMucLuong(job.getMucLuong());
        dto.setHanNop(job.getHanNop());
        dto.setYeuCau(job.getYeuCau());
        dto.setTrangThai(job.getTrangThai());
        dto.setNgayDang(job.getNgayDang());
        dto.setCongTyId(job.getCongty().getId());
        return dto;
    }
} 