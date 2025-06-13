package com.example.QLDA_Project.service;

import com.example.QLDA_Project.dto.JobPostingDto;
import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.TinTuyenDung;
import com.example.QLDA_Project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TinTuyenDungService {
    List<TinTuyenDung> getAllActiveJobs();
    List<TinTuyenDung> getJobsByCompany(CongTy congTy);
    TinTuyenDung getJobById(Long id);
    TinTuyenDung createJob(JobPostingDto jobPostingDto, User hr);
    TinTuyenDung updateJob(Long id, JobPostingDto jobPostingDto, User hr);
    void deleteJob(Long id, User hr);
    
    Page<TinTuyenDung> getAllActiveJobsPaginated(Pageable pageable);
    Page<TinTuyenDung> searchJobs(String keyword, String location, Pageable pageable);
} 