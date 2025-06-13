package com.example.QLDA_Project.dto;

import java.util.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class JobPostingDto {
    private Long id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 200, message = "Tiêu đề không được vượt quá 200 ký tự")
    private String tieuDe;

    @Size(max = 4000, message = "Mô tả công việc không được vượt quá 4000 ký tự")
    private String moTaCongViec;

    @NotBlank(message = "Lĩnh vực không được để trống")
    private String linhVuc;

    @NotBlank(message = "Hình thức làm việc không được để trống")
    private String hinhThucLV;

    @NotBlank(message = "Thành phố làm việc không được để trống")
    private String thanhPhoLV;

    private String quocGiaLV;

    @NotBlank(message = "Địa điểm làm việc không được để trống")
    private String diaDiemLV;

    @NotNull(message = "Mức lương không được để trống")
    @Min(value = 0, message = "Mức lương không được âm")
    private Integer mucLuong;

    @NotNull(message = "Hạn nộp không được để trống")
    @Future(message = "Hạn nộp phải là ngày trong tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hanNop;

    @Size(max = 4000, message = "Yêu cầu không được vượt quá 4000 ký tự")
    private String yeuCau;

    private String trangThai;
    private Date ngayDang;
    private Long congTyId;
} 