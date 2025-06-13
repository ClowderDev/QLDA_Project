package com.example.QLDA_Project.configuration;

import com.example.QLDA_Project.model.Role;
import com.example.QLDA_Project.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            if (roleRepository.findByName(Role.RoleName.ADMIN).isEmpty()) {
                roleRepository.save(new Role(null, Role.RoleName.ADMIN, null));
            }
            if (roleRepository.findByName(Role.RoleName.RECRUITER).isEmpty()) {
                roleRepository.save(new Role(null, Role.RoleName.RECRUITER, null));
            }
            if (roleRepository.findByName(Role.RoleName.HR_STAFF).isEmpty()) {
                roleRepository.save(new Role(null, Role.RoleName.HR_STAFF, null));
            }
            if (roleRepository.findByName(Role.RoleName.CV_STAFF).isEmpty()) {
                roleRepository.save(new Role(null, Role.RoleName.CV_STAFF, null));
            }
            if (roleRepository.findByName(Role.RoleName.CANDIDATE).isEmpty()) {
                roleRepository.save(new Role(null, Role.RoleName.CANDIDATE, null));
            }
        };
    }
} 