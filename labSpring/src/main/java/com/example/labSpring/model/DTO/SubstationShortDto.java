package com.example.labSpring.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.labSpring.model.enums.SubstationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstationShortDto {
    private Long id;
    private String name;
    private String address;
    private String region;
    private SubstationStatus status;
    private Integer commissioningYear;
}
