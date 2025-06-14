package com.example.QLDA_Project.configuration;

import com.example.QLDA_Project.model.Role;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.repository.RoleRepository;
import com.example.QLDA_Project.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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
            }            if(userRepository.findByUsername("adminUser").isEmpty()){
                User adminUser = new User();
                adminUser.setUsername("adminUser");
                adminUser.setEmail("admin@talentHub.com");
                adminUser.setHoTen("Administrator");
                adminUser.setPassword("123");
                adminUser.setEnabled(true);
                adminUser.addRole(roleRepository.findByName(Role.RoleName.ADMIN).orElseThrow());
                userRepository.save(adminUser);
            }            if (userRepository.findByUsername("hrUser").isEmpty()) {
                User hrUser = new User();
                hrUser.setUsername("hrUser");
                hrUser.setEmail("hr@talentHub.com");
                hrUser.setHoTen("HR Staff");
                hrUser.setPassword("123");
                hrUser.setEnabled(true);
                hrUser.addRole(roleRepository.findByName(Role.RoleName.HR_STAFF).orElseThrow());
                userRepository.save(hrUser);
            }            if(userRepository.findByUsername("normalUser").isEmpty()){
                User normalUser = new User();
                normalUser.setUsername("normalUser");
                normalUser.setEmail("candidate@talentHub.com");
                normalUser.setHoTen("Normal User");
                normalUser.setPassword("123");
                normalUser.setEnabled(true);
                normalUser.addRole(roleRepository.findByName(Role.RoleName.CANDIDATE).orElseThrow());
                userRepository.save(normalUser);
            }
        };
    }
} 