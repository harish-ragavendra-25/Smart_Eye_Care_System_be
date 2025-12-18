package com.example.Smart_Eye_Care_be.Controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Smart_Eye_Care_be.Dtos.PatientRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.PatientResponseDto;
import com.example.Smart_Eye_Care_be.Repository.PatientRepo;
import com.example.Smart_Eye_Care_be.Service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PatientController {
    
    private final PatientRepo patientRepo;
    private final PatientService patientService;

    @PostMapping("/create")
    public PatientResponseDto create(@RequestBody PatientRequestDto req) {
        return patientService.createPatient(req);
    }

    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @GetMapping
    public List<PatientResponseDto> getAll() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public PatientResponseDto getById(@PathVariable Long id) {
        return patientService.getPatient(id);
    }

    @PutMapping("/{id}")
    public PatientResponseDto update(@PathVariable Long id, @RequestBody PatientRequestDto req) {
        return patientService.updatePatient(id, req);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return patientService.deletePatient(id);
    }
}
