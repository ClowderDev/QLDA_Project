package com.example.QLDA_Project.service.impl;

import com.example.QLDA_Project.dto.JobPostingDto;
import com.example.QLDA_Project.exception.ResourceNotFoundException;
import com.example.QLDA_Project.model.CongTy;
import com.example.QLDA_Project.model.TinTuyenDung;
import com.example.QLDA_Project.model.User;
import com.example.QLDA_Project.repository.CongTyRepository;
import com.example.QLDA_Project.repository.TinTuyenDungRepository;
import com.example.QLDA_Project.service.TinTuyenDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TinTuyenDungServiceImpl implements TinTuyenDungService {

    @Autowired
    private TinTuyenDungRepository tinTuyenDungRepository;

    @Autowired
    private CongTyRepository congTyRepository;

    @Override
    public List<TinTuyenDung> getAllActiveJobs() {
        return tinTuyenDungRepository.findByTrangThai("ACTIVE");
    }

    @Override
    public List<TinTuyenDung> getJobsByCompany(CongTy congTy) {
        return tinTuyenDungRepository.findByCongtyAndTrangThai(congTy, "ACTIVE");
    }

    @Override
    public TinTuyenDung getJobById(Long id) {
        return tinTuyenDungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tin tuyển dụng với id: " + id));
    }

    @Override
    @Transactional
    public TinTuyenDung createJob(JobPostingDto jobPostingDto, User hr) {
        CongTy congTy = hr.getCongTy();
        if (congTy == null) {
            throw new IllegalStateException("HR không thuộc về công ty nào");
        }

        TinTuyenDung job = new TinTuyenDung();
        updateJobFromDto(job, jobPostingDto);
        job.setCongty(congTy);
        return tinTuyenDungRepository.save(job);
    }

    @Override
    @Transactional
    public TinTuyenDung updateJob(Long id, JobPostingDto jobPostingDto, User hr) {
        TinTuyenDung job = getJobById(id);
        
        if (!job.getCongty().getUsers().contains(hr)) {
            throw new IllegalStateException("Bạn không có quyền chỉnh sửa tin tuyển dụng này");
        }

        updateJobFromDto(job, jobPostingDto);
        job.setTrangThai("ACTIVE");
        return tinTuyenDungRepository.save(job);
    }

    @Override
    @Transactional
    public void deleteJob(Long id, User hr) {
        TinTuyenDung job = getJobById(id);
        
        if (!job.getCongty().getUsers().contains(hr)) {
            throw new IllegalStateException("Bạn không có quyền xóa tin tuyển dụng này");
        }

        tinTuyenDungRepository.delete(job);
    }

    private void updateJobFromDto(TinTuyenDung job, JobPostingDto dto) {
        job.setTieuDe(dto.getTieuDe());
        job.setMoTaCongViec(dto.getMoTaCongViec());
        job.setLinhVuc(dto.getLinhVuc());
        job.setHinhThucLV(dto.getHinhThucLV());
        job.setThanhPhoLV(dto.getThanhPhoLV());
        job.setQuocGiaLV(dto.getQuocGiaLV());
        job.setDiaDiemLV(dto.getDiaDiemLV());
        job.setMucLuong(dto.getMucLuong());
        job.setHanNop(dto.getHanNop());
        job.setYeuCau(dto.getYeuCau());
        job.setTrangThai(dto.getTrangThai());
        job.setNgayDang(dto.getNgayDang());
    }

    @Override
    public Page<TinTuyenDung> getAllActiveJobsPaginated(Pageable pageable) {
        return tinTuyenDungRepository.findByTrangThai("ACTIVE", pageable);
    }

    @Override
    public Page<TinTuyenDung> searchJobs(String keyword, String location, Pageable pageable) {
        Specification<TinTuyenDung> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            predicates.add(cb.equal(root.get("trangThai"), "ACTIVE"));
            
            if (keyword != null && !keyword.isEmpty()) {
                String likeKeyword = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("tieuDe")), likeKeyword),
                    cb.like(cb.lower(root.get("moTaCongViec")), likeKeyword),
                    cb.like(cb.lower(root.get("linhVuc")), likeKeyword),
                    cb.like(cb.lower(root.get("congty").get("tenCongTy")), likeKeyword)
                ));
            }
            
            if (location != null && !location.isEmpty()) {
                String likeLocation = "%" + location.toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("thanhPhoLV")), likeLocation),
                    cb.like(cb.lower(root.get("diaDiemLV")), likeLocation),
                    cb.like(cb.lower(root.get("quocGiaLV")), likeLocation)
                ));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return tinTuyenDungRepository.findAll(spec, pageable);
    }
} 