package com.example.QLDA_Project.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class CongTy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenCongTy;
    private String diaChi;
    private String linhVuc;
    private String website;
    private String email;
    private String hotLine;
    private String logo;
    private String quyMo;
    private String namThanhLap;
    @Column(length = 4000)
    private String moTa;
    private String facebook;
    private String linkedIn;

    @OneToMany(mappedBy = "congty", fetch = FetchType.EAGER)
    private Set<TinTuyenDung> dSTinTD;

    @OneToMany(mappedBy = "congTy", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users;
}
