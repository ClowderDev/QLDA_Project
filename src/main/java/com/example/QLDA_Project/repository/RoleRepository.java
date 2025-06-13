package com.example.QLDA_Project.repository;

import com.example.QLDA_Project.model.Role;
import com.example.QLDA_Project.model.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
} 