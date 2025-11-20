package com.example.Smart_Eye_Care_be.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Smart_Eye_Care_be.Dtos.PatientRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.PatientResponseDto;
import com.example.Smart_Eye_Care_be.Repository.PatientRepo;
import com.example.Smart_Eye_Care_be.Service.PatientService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {
    
    private final PatientRepo patientRepo;
    private final PatientService patientService;

    @PostMapping("/create")
    public PatientResponseDto create(@RequestBody PatientRequestDto req) {
        return patientService.createPatient(req);
    }

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
