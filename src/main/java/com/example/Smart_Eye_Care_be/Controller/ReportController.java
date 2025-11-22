package com.example.Smart_Eye_Care_be.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Smart_Eye_Care_be.Dtos.ReportRequestDto;
import com.example.Smart_Eye_Care_be.Dtos.ReportUpdateDto;
import com.example.Smart_Eye_Care_be.Models.ReportModel;
import com.example.Smart_Eye_Care_be.Service.ReportService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create")
    public ReportModel createReport(@RequestBody ReportRequestDto request) {
        return reportService.createReport(request);
    }

    // Adding Images to the report
    @PostMapping("/addImages/{id}")
    public ReportModel addImagesToReport(@PathVariable Long id,@RequestBody List<String> imageUrls) {
        return reportService.addImagesToReport(id, imageUrls);
    }

    // Getting List of Report by Using Patient and Doctor Id
    @GetMapping("/byPatientAndDoctor/{patientId}/{doctorId}")
    public List<ReportModel> getReportsByPatientAndDoctor(
            @PathVariable Long patientId,
            @PathVariable Long doctorId) {
        return reportService.getReportsByPatientAndDoctor(patientId, doctorId);
    }
    

    @GetMapping("/{id}")
    public ReportModel getReport(@PathVariable Long id) {
        return reportService.getReportById(id);
    }

    @GetMapping("/")
    public List<ReportModel> getAllReports() {
        return reportService.getAllReports();
    }

    @PutMapping("/{id}")
    public ReportModel UpdateReport(@PathVariable Long id, @RequestBody ReportUpdateDto request) {
        return reportService.updateReport(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return "Report deleted successfully";
    }
}
