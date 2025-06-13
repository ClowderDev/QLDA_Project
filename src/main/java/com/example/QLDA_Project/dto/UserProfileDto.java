package com.example.QLDA_Project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class UserProfileDto {
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    private String hoTen;

    @Email(message = "Định dạng email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Định dạng số điện thoại không hợp lệ")
    private String soDienThoai;

    @Size(max = 200, message = "Chuyên ngành không được vượt quá 200 ký tự")
    private String chuyenNganh;

    @Size(max = 500, message = "Địa chỉ không được vượt quá 500 ký tự")
    private String diaChi;

    @Size(max = 4000, message = "Giới thiệu không được vượt quá 4000 ký tự")
    private String gioiThieu;

    @Min(value = 0, message = "Năm kinh nghiệm không được âm")
    @Max(value = 100, message = "Năm kinh nghiệm không hợp lệ")
    private Integer namKinhNghiem;

    @Min(value = 0, message = "Lương mong muốn không được âm")
    private Integer luongMongMuon;

    @Min(value = 0, message = "Lương hiện tại không được âm")
    private Integer luongHienTai;

    @Size(max = 200, message = "Ngôn ngữ không được vượt quá 200 ký tự")
    private String ngonNgu;

    @Min(value = 18, message = "Tuổi phải từ 18 trở lên")
    @Max(value = 100, message = "Tuổi không hợp lệ")
    private Integer tuoi;

    private String gioiTinh;

    @Size(min = 8, max = 120, message = "Mật khẩu phải từ 8 đến 120 ký tự")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$",
            message = "Mật khẩu phải chứa ít nhất một chữ số, một chữ hoa, một chữ thường và một ký tự đặc biệt")
    private String currentPassword;

    @Size(min = 8, max = 120, message = "Mật khẩu phải từ 8 đến 120 ký tự")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$",
            message = "Mật khẩu phải chứa ít nhất một chữ số, một chữ hoa, một chữ thường và một ký tự đặc biệt")
    private String newPassword;
} 