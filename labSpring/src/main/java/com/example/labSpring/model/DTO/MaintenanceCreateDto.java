package com.example.labSpring.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.labSpring.model.enums.WorkType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceCreateDto {
    private WorkType workType;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double cost;
    private String contractor;
}
