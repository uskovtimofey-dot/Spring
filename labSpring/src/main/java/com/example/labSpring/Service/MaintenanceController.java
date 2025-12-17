package com.example.labSpring.Service;

import com.example.labSpring.Service.MaintenanceService;
import com.example.labSpring.model.DTO.MaintenanceCreateDto;
import com.example.labSpring.model.DTO.MaintenanceDto;
import com.example.labSpring.model.DTO.MaintenanceFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping("/substations/{substationId}/maintenance")
    public ResponseEntity<Void> save(
            @PathVariable Long substationId,
            @RequestBody MaintenanceCreateDto dto) {
        maintenanceService.save(substationId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/substations/{substationId}/maintenance")
    public ResponseEntity<List<MaintenanceDto>> getBySubstationId(
            @PathVariable Long substationId,
            @RequestParam(required = false) String workType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateTo) {

        MaintenanceFilterDto filter = new MaintenanceFilterDto();
        if (workType != null) {
            filter.setWorkType(com.example.labSpring.model.enums.WorkType.valueOf(workType));
        }
        filter.setStartDateFrom(startDateFrom);
        filter.setStartDateTo(startDateTo);

        return ResponseEntity.ok(maintenanceService.getBySubstationIdWithFilter(substationId, filter));
    }

    @GetMapping("/substations/{substationId}/maintenance/stats")
    public ResponseEntity<MaintenanceService.MaintenanceStatsDto> getStatsBySubstationId(
            @PathVariable Long substationId) {
        return ResponseEntity.ok(maintenanceService.getStatsBySubstationId(substationId));
    }

    @GetMapping("/maintenance/{id}")
    public ResponseEntity<MaintenanceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceService.getById(id));
    }

    @PutMapping("/maintenance/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody MaintenanceDto dto) {
        maintenanceService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/maintenance/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        maintenanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
