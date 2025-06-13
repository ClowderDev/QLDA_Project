package com.example.QLDA_Project.model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 100)
    @Column(name = "ho_ten")
    private String hoTen;

    @Size(max = 15)
    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "enabled", columnDefinition = "NUMBER(1,0) DEFAULT 1")
    private Boolean enabled = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private String chuyenNganh;
    private int namKinhNghiem;
    private int tuoi;
    private int luongHienTai;
    private int luongMongMuon;
    private String ngonNgu;
    private String gioiTinh;
    @Column(length = 4000)
    private String gioiThieu;
    private String faceBook;
    private String linkedIn;
    private String diaChi;
    private String hinhAnh;
    private Date ngayTao;
    @Column(name = "cv_file")
    private String cvFile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<DonUngTuyen> dSDonUngTuyen;

    @OneToMany(mappedBy = "nhanVienTD", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<PhongVan> dSPhongVan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cong_ty_id", referencedColumnName = "id")
    @JsonIgnore
    private CongTy congTy;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<HocVan> dSHocVan;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<KinhNghiemLamViec> dSKinhNghiemLamViec;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ThanhTuu> dSThanhTuu;

    @OneToMany(mappedBy = "ungVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UngVienYeuThich> dSUngVienYeuThich;

    @OneToMany(mappedBy = "nhaTuyenDung", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UngVienYeuThich> dSNhaTDYeuThich;

    @OneToMany(mappedBy = "ungVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TinYeuThich> dSUngVienYeuThichTinTD;

    public void addRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        userRoles.add(userRole);
    }

    public void removeRole(Role role) {
        userRoles.removeIf(ur -> ur.getRole().equals(role));
    }
}
