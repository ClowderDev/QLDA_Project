package com.example.QLDA_Project.service;

import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.User;

import java.util.Optional;

public interface CongTyService {
    Optional<CongTy> getCompanyById(Long id);
    Optional<CongTy> getCompanyByUser(User user);
} 