package com.example.labSpring.Service;

import com.example.labSpring.Repository.SubstationRepository;
import com.example.labSpring.model.DTO.MaintenanceShortDto;
import com.example.labSpring.model.DTO.SubstationDto;
import com.example.labSpring.model.DTO.SubstationShortDto;
import com.example.labSpring.model.Domain.Substation;
import com.example.labSpring.model.Exception.ScadaException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubstationService {
    private final SubstationRepository substationRepository;

    public void save(SubstationDto dto) {
        Substation substation = convertToEntity(dto);
        substationRepository.save(substation);
    }

    public List<SubstationShortDto> getAll() {
        return substationRepository.findAll().stream()
                .map(this::convertToShortDto)
                .collect(Collectors.toList());
    }

    public SubstationDto getById(Long id) {
        Substation substation = substationRepository.findById(id)
                .orElseThrow(() -> new ScadaException("Substation not found with id: " + id));
        return convertToDto(substation);
    }

    public void update(Long id, SubstationDto dto) {
        Substation substation = substationRepository.findById(id)
                .orElseThrow(() -> new ScadaException("Substation not found with id: " + id));

        substation.setName(dto.getName());
        substation.setAddress(dto.getAddress());
        substation.setRegion(dto.getRegion());
        substation.setVoltageLevel(dto.getVoltageLevel());
        substation.setPower(dto.getPower());
        substation.setStatus(dto.getStatus());
        substation.setCommissioningYear(dto.getCommissioningYear());

        substationRepository.save(substation);
    }

    public void delete(Long id) {
        Substation substation = substationRepository.findById(id)
                .orElseThrow(() -> new ScadaException("Substation not found with id: " + id));

        if (!substation.getMaintenanceRecords().isEmpty()) {
            throw new ScadaException("Cannot delete substation with existing maintenance records");
        }

        substationRepository.delete(substation);
    }

    private Substation convertToEntity(SubstationDto dto) {
        Substation substation = new Substation();
        substation.setId(dto.getId());
        substation.setName(dto.getName());
        substation.setAddress(dto.getAddress());
        substation.setRegion(dto.getRegion());
        substation.setVoltageLevel(dto.getVoltageLevel());
        substation.setPower(dto.getPower());
        substation.setStatus(dto.getStatus());
        substation.setCommissioningYear(dto.getCommissioningYear());
        return substation;
    }

    private SubstationDto convertToDto(Substation substation) {
        List<MaintenanceShortDto> maintenanceDtos = substation.getMaintenanceRecords().stream()
                .map(this::convertToMaintenanceShortDto)
                .collect(Collectors.toList());

        return new SubstationDto(
                substation.getId(),
                substation.getName(),
                substation.getAddress(),
                substation.getRegion(),
                substation.getVoltageLevel(),
                substation.getPower(),
                substation.getStatus(),
                substation.getCommissioningYear(),
                maintenanceDtos
        );
    }

    private SubstationShortDto convertToShortDto(Substation substation) {
        return new SubstationShortDto(
                substation.getId(),
                substation.getName(),
                substation.getAddress(),
                substation.getRegion(),
                substation.getStatus(),
                substation.getCommissioningYear()
        );
    }

    private MaintenanceShortDto convertToMaintenanceShortDto(com.example.labSpring.model.Domain.MaintenanceRecord record) {
        return new MaintenanceShortDto(
                record.getId(),
                record.getWorkType(),
                record.getDescription(),
                record.getStartDate(),
                record.getEndDate(),
                record.getCost()
        );
    }

}
