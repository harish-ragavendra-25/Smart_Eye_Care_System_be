package com.example.Smart_Eye_Care_be.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.Smart_Eye_Care_be.Dtos.DoctorRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.DoctorResponseDto;
import com.example.Smart_Eye_Care_be.Service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:5173", allowCredentials="true")
public class DoctorController {
    
    private final DoctorService doctorService;

    @PostMapping("/create")
    public DoctorResponseDto create(@RequestBody DoctorRequestDto doc) {
        return doctorService.createDoctor(doc);
    }

    @GetMapping("/{id}")
    public DoctorResponseDto get(@PathVariable Long id) {
        return doctorService.getDoctor(id);
    }

    @GetMapping("/")
    public List<DoctorResponseDto> getAll() {
        return doctorService.getAllDoctors();
    }

    @PutMapping("/{id}")
    public DoctorResponseDto update(@PathVariable Long id, @RequestBody DoctorRequestDto req) {
        return doctorService.updateDoctor(id, req);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return doctorService.deleteDoctor(id);
    }
    
}
