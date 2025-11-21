package com.example.Smart_Eye_Care_be.Dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String contactNumber;
    private String address;
}
