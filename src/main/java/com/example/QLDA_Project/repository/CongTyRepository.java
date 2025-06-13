package com.example.QLDA_Project.repository;

import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CongTyRepository extends JpaRepository<CongTy, Long>, JpaSpecificationExecutor<CongTy> {
    @Query("SELECT c FROM CongTy c JOIN c.users u WHERE u = :user")
    Optional<CongTy> findByUser(@Param("user") User user);

    @Query("SELECT c FROM CongTy c WHERE " +
           "LOWER(c.tenCongTy) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.linhVuc) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.moTa) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<CongTy> searchCompanies(@Param("keyword") String keyword, Pageable pageable);
} 