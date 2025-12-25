package com.example.Smart_Eye_Care_be.Controller;

import java.util.List;

import com.example.Smart_Eye_Care_be.Config.CustomUserDetails;
import com.example.Smart_Eye_Care_be.Dtos.PatientRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.PatientResponseDto;
import com.example.Smart_Eye_Care_be.Service.PatientService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PatientController {

    private final PatientService patientService;

    // ================= CREATE PATIENT =================
    @PostMapping("/create")
    public PatientResponseDto create(@RequestBody PatientRequestDto req) {
        return patientService.createPatient(req);
    }

    // ================= GET ALL PATIENTS (DOCTOR / ADMIN) =================
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @GetMapping
    public List<PatientResponseDto> getAll() {
        return patientService.getAllPatients();
    }

    // ================= GET PATIENT BY ID (DOCTOR / ADMIN) =================
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @GetMapping("/{id}")
    public PatientResponseDto getById(@PathVariable Long id) {
        return patientService.getPatient(id);
    }

    // ================= UPDATE PATIENT (DOCTOR / ADMIN) =================
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @PutMapping("/{id}")
    public PatientResponseDto update(
            @PathVariable Long id,
            @RequestBody PatientRequestDto req
    ) {
        return patientService.updatePatient(id, req);
    }

    // ================= DELETE PATIENT (ADMIN ONLY) =================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return patientService.deletePatient(id);
    }

    // ================= GET MY PROFILE (PATIENT) =================
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/me")
    public PatientResponseDto getMyProfile() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        return patientService.getPatientByUserId(user.getUserId());
    }
}
