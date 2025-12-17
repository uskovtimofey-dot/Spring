package com.example.labSpring.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.labSpring.model.enums.WorkType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceFilterDto {
    private WorkType workType;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
}
