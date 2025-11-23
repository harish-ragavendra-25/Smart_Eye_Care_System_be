package com.example.Smart_Eye_Care_be.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String contactNumber;
    private String address;
}
