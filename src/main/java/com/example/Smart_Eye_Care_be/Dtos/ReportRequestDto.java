package com.example.Smart_Eye_Care_be.Dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {
    private Long patientId;                
    private Long doctorId;                  
    private String prediction;              // e.g., "Diabetic Retinopathy"
    private String severity;                // e.g., "Mild", "Moderate", "Severe"
    private String doctorPrescription;      
    private List<String> imageUrls;  
}
