package com.example.Smart_Eye_Care_be.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Doctor")
public class DoctorModel {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long doctorId;

    @OneToOne
    @JoinColumn(name="userId",nullable=false,unique=true)
    private UserModel user;

    private String firstName;
    private String lastName;
    private String specialization;
    private String contactNumber;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
