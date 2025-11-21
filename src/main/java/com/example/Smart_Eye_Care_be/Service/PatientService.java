package com.example.Smart_Eye_Care_be.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Smart_Eye_Care_be.Dtos.PatientRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.PatientResponseDto;
import com.example.Smart_Eye_Care_be.Models.PatientModel;
import com.example.Smart_Eye_Care_be.Models.UserModel;
import com.example.Smart_Eye_Care_be.Repository.PatientRepo;
import com.example.Smart_Eye_Care_be.Repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final UserRepo userRepo;
    private final PatientRepo patientRepo;


    private PatientResponseDto mapToResponse(PatientModel p) {
        PatientResponseDto dto = new PatientResponseDto();

        dto.setPatientId(p.getPatientId());
        dto.setUserId(p.getUser().getUserId());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setDateOfBirth(p.getDateOfBirth());
        dto.setContactNumber(p.getContactNumber());
        dto.setAddress(p.getAddress());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());

        return dto;
    }

    @Transactional
    public PatientResponseDto createPatient(PatientRequestDto req) {

        if (patientRepo.existsByUser_UserId(req.getUserId())) {
            throw new IllegalArgumentException("Patient already exists for userId: " + req.getUserId());
        }

        UserModel user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + req.getUserId()));

        PatientModel patient = new PatientModel();
        patient.setUser(user);
        patient.setFirstName(req.getFirstName());
        patient.setLastName(req.getLastName());
        patient.setDateOfBirth(req.getDateOfBirth());
        patient.setContactNumber(req.getContactNumber());
        patient.setAddress(req.getAddress());

        patient.setCreatedAt(LocalDateTime.now());
        patient.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(patientRepo.save(patient));
    }

    public PatientResponseDto getPatient(Long id) {
        PatientModel patient = patientRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found: " + id));
        return mapToResponse(patient);
    }

    public List<PatientResponseDto> getAllPatients() {
        return patientRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PatientResponseDto updatePatient(Long id, PatientRequestDto req) {

        PatientModel existing = patientRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found: " + id));

        if (req.getFirstName() != null) existing.setFirstName(req.getFirstName());
        if (req.getLastName() != null) existing.setLastName(req.getLastName());
        if (req.getDateOfBirth() != null) existing.setDateOfBirth(req.getDateOfBirth());
        if (req.getContactNumber() != null) existing.setContactNumber(req.getContactNumber());
        if (req.getAddress() != null) existing.setAddress(req.getAddress());

        existing.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(patientRepo.save(existing));
    }

    @Transactional
    public String deletePatient(Long id) {
        PatientModel existing = patientRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found: " + id));

        patientRepo.delete(existing);
        return "Patient deleted successfully";
    }
}
