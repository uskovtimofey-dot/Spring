package com.example.labSpring.Service;

import com.example.labSpring.model.DTO.MaintenanceDto;
import com.example.labSpring.model.DTO.MaintenanceFilterDto;
import com.example.labSpring.model.Domain.MaintenanceRecord;
import com.example.labSpring.model.Domain.Substation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import com.example.labSpring.Repository.MaintenanceRepository;
import com.example.labSpring.Repository.SubstationRepository;
import com.example.labSpring.model.DTO.MaintenanceCreateDto;
import com.example.labSpring.model.Exception.ScadaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final SubstationRepository substationRepository;

    public void save(Long substationId, MaintenanceCreateDto dto) {
        Substation substation = substationRepository.findById(substationId)
                .orElseThrow(() -> new ScadaException("Substation not found with id: " + substationId));

        MaintenanceRecord record = new MaintenanceRecord();
        record.setWorkType(dto.getWorkType());
        record.setDescription(dto.getDescription());
        record.setStartDate(dto.getStartDate());
        record.setEndDate(dto.getEndDate());
        record.setCost(dto.getCost());
        record.setContractor(dto.getContractor());
        record.setSubstation(substation);

        maintenanceRepository.save(record);
    }

    public List<MaintenanceDto> getBySubstationId(Long substationId) {
        return maintenanceRepository.findBySubstationId(substationId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDto> getBySubstationIdWithFilter(Long substationId, MaintenanceFilterDto filter) {
        List<MaintenanceRecord> records;

        if (filter.getWorkType() != null && filter.getStartDateFrom() != null && filter.getStartDateTo() != null) {
            records = maintenanceRepository.findBySubstationIdAndWorkTypeAndStartDateBetween(
                    substationId, filter.getWorkType(), filter.getStartDateFrom(), filter.getStartDateTo());
        } else if (filter.getWorkType() != null) {
            records = maintenanceRepository.findBySubstationIdAndWorkType(substationId, filter.getWorkType());
        } else if (filter.getStartDateFrom() != null && filter.getStartDateTo() != null) {
            records = maintenanceRepository.findBySubstationIdAndStartDateBetween(
                    substationId, filter.getStartDateFrom(), filter.getStartDateTo());
        } else {
            records = maintenanceRepository.findBySubstationId(substationId);
        }

        return records.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MaintenanceDto getById(Long id) {
        MaintenanceRecord record = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ScadaException("Maintenance record not found with id: " + id));
        return convertToDto(record);
    }

    public void update(Long id, MaintenanceDto dto) {
        MaintenanceRecord record = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ScadaException("Maintenance record not found with id: " + id));

        record.setWorkType(dto.getWorkType());
        record.setDescription(dto.getDescription());
        record.setStartDate(dto.getStartDate());
        record.setEndDate(dto.getEndDate());
        record.setCost(dto.getCost());
        record.setContractor(dto.getContractor());

        maintenanceRepository.save(record);
    }

    public void delete(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ScadaException("Maintenance record not found with id: " + id);
        }
        maintenanceRepository.deleteById(id);
    }

    public MaintenanceStatsDto getStatsBySubstationId(Long substationId) {
        List<MaintenanceRecord> records = maintenanceRepository.findBySubstationId(substationId);

        long totalCount = records.size();

        double totalCost = records.stream()
                .mapToDouble(record -> record.getCost() != null ? record.getCost() : 0.0)
                .sum();

        double avgDuration = records.stream()
                .filter(record -> record.getStartDate() != null && record.getEndDate() != null)
                .mapToLong(record -> java.time.temporal.ChronoUnit.DAYS.between(record.getStartDate(), record.getEndDate()))
                .average()
                .orElse(0.0);

        return new MaintenanceStatsDto(totalCount, totalCost, avgDuration);
    }

    private MaintenanceDto convertToDto(MaintenanceRecord record) {
        return new MaintenanceDto(
                record.getId(),
                record.getWorkType(),
                record.getDescription(),
                record.getStartDate(),
                record.getEndDate(),
                record.getCost(),
                record.getContractor(),
                record.getSubstation().getId()
        );
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MaintenanceStatsDto {
        private Long totalRecords;
        private Double totalCost;
        private Double avgDurationDays;
    }

}
