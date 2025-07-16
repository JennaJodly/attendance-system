
package com.example.attendance.repositories;

import com.example.attendance.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByFingerprintId(String fingerprintId);
}
