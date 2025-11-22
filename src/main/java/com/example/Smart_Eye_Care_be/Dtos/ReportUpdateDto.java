package com.example.Smart_Eye_Care_be.Dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateDto {
        private String prediction;
    private String severity;
    private String doctorPrescription;
    private List<Long> deleteImageIds;
}
