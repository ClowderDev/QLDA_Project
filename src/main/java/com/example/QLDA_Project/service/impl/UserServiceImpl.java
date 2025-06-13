package com.example.QLDA_Project.service.impl;

import com.example.QLDA_Project.dto.UserProfileDto;
import com.example.QLDA_Project.dto.UserRegistrationDto;
import com.example.QLDA_Project.exception.ResourceNotFoundException;
import com.example.QLDA_Project.model.Role;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.repository.RoleRepository;
import com.example.QLDA_Project.repository.UserRepository;
import com.example.QLDA_Project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        System.out.println("Tạo user");
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setHoTen(registrationDto.getHoTen());
        user.setSoDienThoai(registrationDto.getSoDienThoai());
        user.setEnabled(true);

        System.out.println("Tìm role");
        Role role = roleRepository.findByName(registrationDto.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        System.out.println("Role la: " + role.getName());
        user.addRole(role);

        System.out.println("Lưu user");
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUserProfile(Long userId, UserProfileDto profileDto) {
        User user = getUserById(userId);

        if (profileDto.getHoTen() != null) {
            user.setHoTen(profileDto.getHoTen());
        }
        if (profileDto.getEmail() != null && !profileDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(profileDto.getEmail())) {
                throw new IllegalArgumentException("Email is already in use");
            }
            user.setEmail(profileDto.getEmail());
        }
        if (profileDto.getSoDienThoai() != null) {
            user.setSoDienThoai(profileDto.getSoDienThoai());
        }
        if (profileDto.getChuyenNganh() != null) {
            user.setChuyenNganh(profileDto.getChuyenNganh());
        }
        if (profileDto.getDiaChi() != null) {
            user.setDiaChi(profileDto.getDiaChi());
        }
        if (profileDto.getGioiThieu() != null) {
            user.setGioiThieu(profileDto.getGioiThieu());
        }
        if (profileDto.getNamKinhNghiem() != null) {
            user.setNamKinhNghiem(profileDto.getNamKinhNghiem());
        }
        if (profileDto.getLuongMongMuon() != null) {
            user.setLuongMongMuon(profileDto.getLuongMongMuon());
        }
        if (profileDto.getLuongHienTai() != null) {
            user.setLuongHienTai(profileDto.getLuongHienTai());
        }
        if (profileDto.getNgonNgu() != null) {
            user.setNgonNgu(profileDto.getNgonNgu());
        }
        if (profileDto.getTuoi() != null) {
            user.setTuoi(profileDto.getTuoi());
        }

        if( profileDto.getGioiTinh() != null) {
            user.setGioiTinh(profileDto.getGioiTinh());
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = getUserById(userId);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getCurrentUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getCurrentUserWithCompanyDetails(Authentication authentication) {
        return userRepository.findByUsernameWithCongTyDetails(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User with company details not found"));
    }
} 