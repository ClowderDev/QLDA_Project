package com.example.QLDA_Project.controller;

import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.service.CongTyService;
import com.example.QLDA_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/hr/company")
@PreAuthorize("hasAnyRole('HR_STAFF', 'RECRUITER', 'CV_STAFF')")
public class HrCompanyController {

    private static final Logger logger = LoggerFactory.getLogger(HrCompanyController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CongTyService congTyService;

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentCompany(Authentication authentication) {
        User currentUser = userService.getCurrentUserWithCompanyDetails(authentication);

        logger.info("Current User: {}", currentUser.getUsername());
        logger.info("Current User Company (before null check): {}", currentUser.getCongTy());
        
        CongTy company = currentUser.getCongTy();
        
        if (company == null) {
            Map<String, String> errorResponse = Collections.singletonMap("error", "User is not associated with any company");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        return ResponseEntity.ok(company);
    }
} 