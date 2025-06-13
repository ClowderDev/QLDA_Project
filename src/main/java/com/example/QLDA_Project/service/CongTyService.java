package com.example.QLDA_Project.service;

import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CongTyService {
    Optional<CongTy> getCompanyById(Long id);
    Optional<CongTy> getCompanyByUser(User user);

    Page<CongTy> getAllCompanies(Pageable pageable);
    
    Page<CongTy> searchCompanies(String keyword, String location, Pageable pageable);

    List<CongTy> getFeaturedCompanies(Pageable pageable);
} 