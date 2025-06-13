package com.example.QLDA_Project.repository;

import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.TinTuyenDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TinTuyenDungRepository extends JpaRepository<TinTuyenDung, Long>, JpaSpecificationExecutor<TinTuyenDung> {
    List<TinTuyenDung> findByTrangThai(String trangThai);
    Page<TinTuyenDung> findByTrangThai(String trangThai, Pageable pageable);
    List<TinTuyenDung> findByCongtyAndTrangThai(CongTy congTy, String trangThai);
} 