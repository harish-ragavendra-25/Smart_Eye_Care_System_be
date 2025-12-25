package com.example.Smart_Eye_Care_be.Dtos;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ReportResponseDto {

    private Long reportId;

    private PatientResponseDto patient;
    private DoctorResponseDto doctor;

    private String prediction;
    private String severity;
    private String doctorPrescription;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ReportImageDto> images;
}
