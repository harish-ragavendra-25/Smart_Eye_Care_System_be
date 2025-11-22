package com.example.Smart_Eye_Care_be.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.Smart_Eye_Care_be.Dtos.ReportRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.ReportUpdateDto;
import com.example.Smart_Eye_Care_be.Models.DoctorModel;
import com.example.Smart_Eye_Care_be.Models.PatientModel;
import com.example.Smart_Eye_Care_be.Models.ReportImageModel;
import com.example.Smart_Eye_Care_be.Models.ReportModel;
import com.example.Smart_Eye_Care_be.Repository.DoctorRepo;
import com.example.Smart_Eye_Care_be.Repository.PatientRepo;
import com.example.Smart_Eye_Care_be.Repository.ReportImageRepo;
import com.example.Smart_Eye_Care_be.Repository.ReportRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepo reportRepo;
    private final ReportImageRepo imageRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;

    @Transactional
    public ReportModel createReport(ReportRequestDto request) {

        PatientModel patient = patientRepo.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        DoctorModel doctor = doctorRepo.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));


        List<Long> imageIds = new ArrayList<>();
        for (String url : request.getImageUrls()) {
            ReportImageModel img = new ReportImageModel();
            img.setImgUrl(url);
            ReportImageModel savedImg = imageRepo.save(img);
            imageIds.add(savedImg.getId());
        }

        // Create report with image IDs
        ReportModel report = new ReportModel();
        report.setPatient(patient);
        report.setDoctor(doctor);
        report.setPrediction(request.getPrediction());
        report.setSeverity(request.getSeverity());
        report.setDoctorPrescription(request.getDoctorPrescription());
        report.setListImageIds(imageIds);
        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());

        return reportRepo.save(report);
    }

    public ReportModel getReportById(Long id) {
        return reportRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Report not found: " + id));
    }

    public List<ReportModel> getAllReports() {
        return reportRepo.findAll();
    }

    @Transactional
    public void deleteReport(Long reportId) {
        ReportModel report = reportRepo.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        // Delete images first
        if (report.getListImageIds() != null) {
            for (Long imgId : report.getListImageIds()) {
                imageRepo.deleteById(imgId);
            }
        }

        // Delete report
        reportRepo.delete(report);
    }


    @Transactional
    public ReportModel updateReport(Long reportId, ReportUpdateDto request) {
        // Fetch the existing report
        ReportModel report = reportRepo.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        // Update basic fields
        report.setPrediction(request.getPrediction());
        report.setSeverity(request.getSeverity());
        report.setDoctorPrescription(request.getDoctorPrescription());
        report.setUpdatedAt(LocalDateTime.now());

        // Delete the images requested for deletion
        if (request.getDeleteImageIds() != null) {
            for (Long imgId : request.getDeleteImageIds()) {
                imageRepo.deleteById(imgId); // Delete from DB
                report.getListImageIds().remove(imgId); // Remove reference from report
            }
        }

        return reportRepo.save(report);
    }

    @Transactional
    public ReportModel addImagesToReport(Long reportId, List<String> newImageUrls) {
        // Fetch the report
        ReportModel report = reportRepo.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found: " + reportId));

        if (newImageUrls == null || newImageUrls.isEmpty()) {
            return report; // Nothing to add
        }

        // Save each new image and collect their IDs
        for (String url : newImageUrls) {
            ReportImageModel img = new ReportImageModel();
            img.setImgUrl(url);
            ReportImageModel savedImg = imageRepo.save(img);

            // Add the image ID to the report's list
            report.getListImageIds().add(savedImg.getId());
        }

        // Update the timestamp
        report.setUpdatedAt(LocalDateTime.now());

        // Save updated report
        return reportRepo.save(report);
    }

    public List<ReportModel> getReportsByPatientAndDoctor(Long patientId, Long doctorId) {
        return reportRepo.findByPatient_PatientIdAndDoctor_DoctorId(patientId, doctorId);
    }

    // public List<ReportModel> getReportsByPatient(Long patientId) {
    //     return reportRepo.findByPatient_PatientId(patientId);
    // }

    // public List<ReportModel> getReportsByDoctor(Long doctorId) {
    //     return reportRepo.findByDoctor_DoctorId(doctorId);
    // }

}
