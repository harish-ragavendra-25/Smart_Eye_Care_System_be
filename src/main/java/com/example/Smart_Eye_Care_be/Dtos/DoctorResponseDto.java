package com.example.Smart_Eye_Care_be.Dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDto {
    private Long doctorId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String contactNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
