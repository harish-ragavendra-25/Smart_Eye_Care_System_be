package com.example.Smart_Eye_Care_be.Dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {
    private Long patientId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String contactNumber;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
