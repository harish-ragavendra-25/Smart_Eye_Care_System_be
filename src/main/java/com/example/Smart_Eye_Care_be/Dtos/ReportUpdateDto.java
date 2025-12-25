package com.example.Smart_Eye_Care_be.Dtos;

import java.util.List;
import lombok.Data;

@Data
public class ReportUpdateDto {
    private String prediction;
    private String severity;
    private String doctorPrescription;
    private List<Long> deleteImageIds;
}
