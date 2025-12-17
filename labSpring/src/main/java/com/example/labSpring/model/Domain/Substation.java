package com.example.labSpring.model.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.labSpring.model.enums.SubstationStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="substations")
public class Substation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "region")
    private String region;

    @Column(name = "voltage_level")
    private Double voltageLevel;

    @Column(name = "power")
    private Double power;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SubstationStatus status;

    @Column(name = "commissioning_year")
    private Integer commissioningYear;

    @OneToMany(mappedBy = "substation", cascade = CascadeType.ALL)
    private List<MaintenanceRecord> maintenanceRecords = new ArrayList<>();

}
