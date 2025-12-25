package com.example.Smart_Eye_Care_be.Controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_Eye_Care_be.Config.CustomUserDetails;
import com.example.Smart_Eye_Care_be.Dtos.*;
import com.example.Smart_Eye_Care_be.Models.DoctorModel;
import com.example.Smart_Eye_Care_be.Models.ReportModel;
import com.example.Smart_Eye_Care_be.Repository.DoctorRepo;
import com.example.Smart_Eye_Care_be.Service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ReportController {

    private final ReportService reportService;
    private final DoctorRepo doctorRepo;

    @PostMapping("/create")
    public ReportResponseDto createReport(@RequestBody ReportRequestDto req) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        ReportModel report =
                reportService.createReport(req, user.getUsername());

        return reportService.mapToResponse(report);
    }

    @GetMapping("/{id}")
    public ReportResponseDto getReport(@PathVariable Long id) {
        return reportService.mapToResponse(
                reportService.getReportById(id)
        );
    }

    @PutMapping("/{id}")
    public ReportResponseDto updateReport(
            @PathVariable Long id,
            @RequestBody ReportUpdateDto dto
    ) {
        return reportService.mapToResponse(
                reportService.updateReport(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public String deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return "Report deleted successfully";
    }

    @GetMapping("/byPatient/{patientId}")
    public List<ReportResponseDto> getByPatient(@PathVariable Long patientId) {
        return reportService.getReportsByPatient(patientId)
                .stream()
                .map(reportService::mapToResponse)
                .toList();
    }

    @GetMapping("/byDoctor/{doctorId}")
    public List<ReportResponseDto> getByDoctor(@PathVariable Long doctorId) {
        return reportService.getReportsByDoctor(doctorId)
                .stream()
                .map(reportService::mapToResponse)
                .toList();
    }

    @GetMapping("/byDoctor/me")
    public List<ReportResponseDto> getMyReports() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        DoctorModel doctor = doctorRepo
                .findByUser_UserName(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return reportService.getReportsByDoctor(doctor.getDoctorId())
                .stream()
                .map(reportService::mapToResponse)
                .toList();
    }

    @GetMapping
    public List<ReportResponseDto> getAllReports() {
        return reportService.getAllReports()
                .stream()
                .map(reportService::mapToResponse)
                .toList();
    }
}
