package com.example.attendance.repositories;

import com.example.attendance.entities.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = """
        SELECT 
            e.id,
            e.first_name,
            e.last_name,
            e.email,
            e.emp_no,
            

            d.name AS department,
            sd.name AS sub_department,
            dv.name AS division,
            des.name AS designation,
            r.name AS region,
            b.name AS branch,
            e.official_no,
            e.photo


        FROM employee e
        LEFT JOIN departmentmstr d ON e.department = d.id
        LEFT JOIN subdepartmentmstr sd ON e.sub_department = sd.id
        LEFT JOIN divisionmstr dv ON e.division = dv.id
        LEFT JOIN designationmstr des ON e.designation = des.id
        LEFT JOIN regionmstr r ON e.region = r.id
        LEFT JOIN branchmstr b ON e.branch = b.id
        WHERE e.active = true
        AND b.active = true
        """, nativeQuery = true)
    List<Object[]> getAllJoinedEmployeesRaw();
}
