package com.example.labSpring.Repository;

import com.example.labSpring.model.Domain.Substation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubstationRepository extends JpaRepository<Substation, Long> {
}
