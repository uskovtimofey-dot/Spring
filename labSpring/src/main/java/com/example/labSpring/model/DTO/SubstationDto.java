package com.example.labSpring.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.labSpring.model.enums.SubstationStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstationDto {
    private Long id;
    private String name;
    private String address;
    private String region;
    private Double voltageLevel;
    private Double power;
    private SubstationStatus status;
    private Integer commissioningYear;
    private List<MaintenanceShortDto> maintenanceRecords;
}
