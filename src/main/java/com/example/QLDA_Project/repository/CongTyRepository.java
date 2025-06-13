package com.example.QLDA_Project.repository;

import com.example.QLDA_Project.model.CongTy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongTyRepository extends JpaRepository<CongTy, Long> {
} 