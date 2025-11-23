package com.example.Smart_Eye_Care_be.Models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
public class ReportModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "patientId",nullable=false)
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "doctorId",nullable=false)
    private DoctorModel doctor;

    private List<Long> listImageIds; // store ReportImage IDs

    private String prediction;            // e.g., "Diabetic Retinopathy"
    private String severity;              // e.g., "Mild", "Moderate", "Severe"
    private String doctorPrescription;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
