package com.example.QLDA_Project.service.impl;

import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.repository.CongTyRepository;
import com.example.QLDA_Project.service.CongTyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CongTyServiceImpl implements CongTyService {

    private final CongTyRepository congTyRepository;

    @Override
    public Optional<CongTy> getCompanyById(Long id) {
        return congTyRepository.findById(id);
    }

    @Override
    public Optional<CongTy> getCompanyByUser(User user) {
        if (user != null && user.getCongTy() != null) {
            return congTyRepository.findById(user.getCongTy().getId());
        }
        return Optional.empty();
    }
} 