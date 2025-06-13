package com.example.QLDA_Project.dto;

import com.example.QLDA_Project.model.Role.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 120, message = "Password must be between 8 and 120 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$",
            message = "Password must contain at least one digit, one uppercase letter, one lowercase letter, and one special character")
    private String password;

    @NotBlank(message = "Họ tên là bắt buộc")
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    private String hoTen;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Định dạng số điện thoại không hợp lệ")
    private String soDienThoai;

    private String chuyenNganh;

    private RoleName role;
} 