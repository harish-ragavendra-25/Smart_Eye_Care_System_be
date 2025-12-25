package com.example.Smart_Eye_Care_be.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {
    private Long patientId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String contactNumber;
    private String address;
    private LocalDateTime createdAt;   // ✅ ADD
    private LocalDateTime updatedAt;   // ✅ ADD

    public void setCreatedAt(LocalDateTime createdAt) {
    }
}
