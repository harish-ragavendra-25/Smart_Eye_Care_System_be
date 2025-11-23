package com.example.Smart_Eye_Care_be.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Smart_Eye_Care_be.Models.ReportImageModel;

@Repository
public interface ReportImageRepo extends JpaRepository<ReportImageModel, Long> {
}
