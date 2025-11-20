package com.example.Smart_Eye_Care_be.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Smart_Eye_Care_be.Models.DoctorModel;

@Repository
public interface DoctorRepo extends JpaRepository<DoctorModel,Long> {
        boolean existsByUser_UserId(Long userId);
}
