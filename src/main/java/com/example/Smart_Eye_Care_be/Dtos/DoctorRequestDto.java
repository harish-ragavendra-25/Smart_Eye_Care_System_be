package com.example.Smart_Eye_Care_be.Dtos;

import lombok.Data;

@Data
public class DoctorRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String contactNumber;
}
