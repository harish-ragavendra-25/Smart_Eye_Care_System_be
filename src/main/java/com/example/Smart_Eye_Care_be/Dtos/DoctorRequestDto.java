package com.example.Smart_Eye_Care_be.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String contactNumber;
}
