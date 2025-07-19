package com.example.attendance.repositories;

import com.example.attendance.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Add custom queries if needed
}
