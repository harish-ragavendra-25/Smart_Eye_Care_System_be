package com.example.Smart_Eye_Care_be.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Smart_Eye_Care_be.Models.ReportModel;

@Repository
public interface ReportRepo extends JpaRepository<ReportModel, Long> {
    List<ReportModel> findByPatient_PatientIdAndDoctor_DoctorId(Long patientId, Long doctorId);
}
