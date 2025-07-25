package com.example.attendance.repositories;

import com.example.attendance.entities.ProductionEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionEntryRepository extends JpaRepository<ProductionEntry, Long> {
}
