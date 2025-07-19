package com.example.attendance.repositories;

import com.example.attendance.entities.countrymstr;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CountrymstrRepository extends JpaRepository<countrymstr, UUID> {
}
