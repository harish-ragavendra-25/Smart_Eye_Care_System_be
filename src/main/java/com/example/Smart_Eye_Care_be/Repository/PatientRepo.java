package com.example.Smart_Eye_Care_be.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Smart_Eye_Care_be.Models.PatientModel;

@Repository
public interface PatientRepo extends JpaRepository<PatientModel,Long> {
}
