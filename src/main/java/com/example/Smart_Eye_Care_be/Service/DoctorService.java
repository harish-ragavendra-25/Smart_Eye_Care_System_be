package com.example.Smart_Eye_Care_be.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.Smart_Eye_Care_be.Dtos.DoctorRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.DoctorResponseDto;
import com.example.Smart_Eye_Care_be.Models.DoctorModel;
import com.example.Smart_Eye_Care_be.Models.UserModel;
import com.example.Smart_Eye_Care_be.Repository.DoctorRepo;
import com.example.Smart_Eye_Care_be.Repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final UserRepo userRepo;

    private DoctorResponseDto mapToResponse(DoctorModel doc) {
        DoctorResponseDto res = new DoctorResponseDto();
        res.setDoctorId(doc.getDoctorId());
        res.setUserId(doc.getUser().getUserId());
        res.setFirstName(doc.getFirstName());
        res.setLastName(doc.getLastName());
        res.setSpecialization(doc.getSpecialization());
        res.setContactNumber(doc.getContactNumber());
        res.setCreatedAt(doc.getCreatedAt());
        res.setUpdatedAt(doc.getUpdatedAt());
        return res;
    }

    @Transactional
    public DoctorResponseDto createDoctor(DoctorRequestDto req){
        
        // checks if the Doctor Found in the user Table or not
        UserModel user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + req.getUserId()));
        // check the doctor already found in the doctor table
        if (doctorRepo.existsByUser_UserId(req.getUserId())) {
            throw new IllegalArgumentException("Doctor already exists for userId: " + req.getUserId());
        }

        DoctorModel doc = new DoctorModel();
        doc.setUser(user);
        doc.setFirstName(req.getFirstName());
        doc.setLastName(req.getLastName());
        doc.setSpecialization(req.getSpecialization());
        doc.setContactNumber(req.getContactNumber());
        doc.setCreatedAt(LocalDateTime.now());
        doc.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(doctorRepo.save(doc));
    }

    public DoctorResponseDto getDoctor(Long id) {
        DoctorModel doc = doctorRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found: " + id));
        return mapToResponse(doc);
    }

    @Transactional
    public DoctorResponseDto updateDoctor(Long id, DoctorRequestDto req) {

        DoctorModel doc = doctorRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found: " + id));

        if (req.getFirstName() != null) doc.setFirstName(req.getFirstName());
        if (req.getLastName() != null) doc.setLastName(req.getLastName());
        if (req.getSpecialization() != null) doc.setSpecialization(req.getSpecialization());
        if (req.getContactNumber() != null) doc.setContactNumber(req.getContactNumber());
        
        doc.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(doctorRepo.save(doc));
    }

    @Transactional
    public String deleteDoctor(Long id) {
        if (!doctorRepo.existsById(id)) {
            throw new NoSuchElementException("Doctor not found: " + id);
        }
        doctorRepo.deleteById(id);
        return "Doctor deleted successfully";
    }
}
