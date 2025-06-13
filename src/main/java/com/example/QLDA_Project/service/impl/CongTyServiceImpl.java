package com.example.QLDA_Project.service.impl;

import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.repository.CongTyRepository;
import com.example.QLDA_Project.service.CongTyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CongTyServiceImpl implements CongTyService {

    @Autowired
    private CongTyRepository congTyRepository;

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

    @Override
    public Page<CongTy> getAllCompanies(Pageable pageable) {
        return congTyRepository.findAll(pageable);
    }

    @Override
    public Page<CongTy> searchCompanies(String keyword, String location, Pageable pageable) {
        Specification<CongTy> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (keyword != null && !keyword.isEmpty()) {
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("tenCongTy")), likeKeyword),
                    cb.like(cb.lower(root.get("linhVuc")), likeKeyword),
                    cb.like(cb.lower(root.get("moTa")), likeKeyword)
                ));
            }
            
            if (location != null && !location.isEmpty()) {
                String likeLocation = "%" + location.toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("diaChi")), likeLocation));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return congTyRepository.findAll(spec, pageable);
    }

    @Override
    public List<CongTy> getFeaturedCompanies(Pageable pageable) {
        return congTyRepository.findAll(pageable).getContent();
    }
} 