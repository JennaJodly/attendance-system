package com.example.attendance.repositories;

import com.example.attendance.entities.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {
    List<EmployeeDocument> findByEmployeeId(Long employeeId);
}

