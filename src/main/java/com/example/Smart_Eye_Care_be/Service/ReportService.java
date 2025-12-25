package com.example.Smart_Eye_Care_be.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Smart_Eye_Care_be.Dtos.*;
import com.example.Smart_Eye_Care_be.Models.*;
import com.example.Smart_Eye_Care_be.Repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepo reportRepo;
    private final ReportImageRepo imageRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;

    // ================= CREATE REPORT =================
    @Transactional
    public ReportModel createReport(ReportRequestDto req, String username) {

        DoctorModel doctor = doctorRepo
                .findByUser_UserName(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        PatientModel patient = patientRepo.findById(req.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Long> imageIds = new ArrayList<>();
        if (req.getImageUrls() != null) {
            for (String url : req.getImageUrls()) {
                ReportImageModel img = new ReportImageModel();
                img.setImgUrl(url);
                imageIds.add(imageRepo.save(img).getId());
            }
        }

        ReportModel report = new ReportModel();
        report.setDoctor(doctor);
        report.setPatient(patient);
        report.setPrediction(req.getPrediction());
        report.setSeverity(req.getSeverity());
        report.setDoctorPrescription(req.getDoctorPrescription());
        report.setListImageIds(imageIds);
        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());

        return reportRepo.save(report);
    }

    // ================= GET =================
    public ReportModel getReportById(Long id) {
        return reportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public List<ReportModel> getAllReports() {
        return reportRepo.findAll();
    }

    public List<ReportModel> getReportsByPatient(Long patientId) {
        return reportRepo.findByPatient_PatientId(patientId);
    }

    public List<ReportModel> getReportsByDoctor(Long doctorId) {
        return reportRepo.findByDoctor_DoctorId(doctorId);
    }

    // ================= UPDATE =================
    @Transactional
    public ReportModel updateReport(Long id, ReportUpdateDto dto) {

        ReportModel report = getReportById(id);

        report.setPrediction(dto.getPrediction());
        report.setSeverity(dto.getSeverity());
        report.setDoctorPrescription(dto.getDoctorPrescription());
        report.setUpdatedAt(LocalDateTime.now());

        if (dto.getDeleteImageIds() != null) {
            for (Long imgId : dto.getDeleteImageIds()) {
                imageRepo.deleteById(imgId);
                report.getListImageIds().remove(imgId);
            }
        }

        return reportRepo.save(report);
    }

    // ================= DELETE =================
    @Transactional
    public void deleteReport(Long id) {

        ReportModel report = getReportById(id);

        if (report.getListImageIds() != null) {
            for (Long imgId : report.getListImageIds()) {
                imageRepo.deleteById(imgId);
            }
        }

        reportRepo.delete(report);
    }

    // ================= MAPPER =================
    public ReportResponseDto mapToResponse(ReportModel report) {

        ReportResponseDto dto = new ReportResponseDto();

        dto.setReportId(report.getReportId());
        dto.setPrediction(report.getPrediction());
        dto.setSeverity(report.getSeverity());
        dto.setDoctorPrescription(report.getDoctorPrescription());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setUpdatedAt(report.getUpdatedAt());

        // =====================================================
        // 🔧 FIX #1 — PATIENT DTO (NO CONSTRUCTOR, ONLY SETTERS)
        // =====================================================
        PatientResponseDto patientDto = new PatientResponseDto();
        patientDto.setPatientId(report.getPatient().getPatientId());
        patientDto.setFirstName(report.getPatient().getFirstName());
        patientDto.setLastName(report.getPatient().getLastName());
        patientDto.setDateOfBirth(report.getPatient().getDateOfBirth());
        patientDto.setContactNumber(report.getPatient().getContactNumber());
        patientDto.setAddress(report.getPatient().getAddress());

        dto.setPatient(patientDto);

        // =====================================================
        // 🔧 FIX #2 — DOCTOR DTO (ALREADY CORRECT)
        // =====================================================
        DoctorResponseDto doctorDto = new DoctorResponseDto();
        doctorDto.setDoctorId(report.getDoctor().getDoctorId());
        doctorDto.setFirstName(report.getDoctor().getFirstName());
        doctorDto.setLastName(report.getDoctor().getLastName());
        doctorDto.setSpecialization(report.getDoctor().getSpecialization());
        doctorDto.setContactNumber(report.getDoctor().getContactNumber());

        dto.setDoctor(doctorDto);

        // =====================================================
        // 🔧 FIX #3 — IMAGE DTOs
        // =====================================================
        List<ReportImageDto> images = report.getListImageIds().stream()
                .map(id -> {
                    ReportImageModel img = imageRepo.findById(id).orElseThrow();
                    ReportImageDto i = new ReportImageDto();
                    i.setId(img.getId());
                    i.setImgUrl(img.getImgUrl());
                    return i;
                }).toList();

        dto.setImages(images);

        return dto;
    }
}
