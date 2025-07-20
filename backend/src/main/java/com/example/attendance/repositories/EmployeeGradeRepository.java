package com.example.attendance.repositories;

import com.example.attendance.entities.EmployeeGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.Map;

public interface EmployeeGradeRepository extends JpaRepository<EmployeeGrade, Long> {
    
    @Query("SELECT eg FROM EmployeeGrade eg WHERE TRIM(LOWER(eg.code)) = LOWER(:code)")
    Optional<EmployeeGrade> findByCode(@Param("code") String code);
    
    @Query("SELECT eg FROM EmployeeGrade eg WHERE eg.active = true ORDER BY eg.level")
    List<EmployeeGrade> findAllActiveOrderByLevel();
    
    @Query("SELECT eg FROM EmployeeGrade eg WHERE eg.level = :level")
    List<EmployeeGrade> findByLevel(@Param("level") Integer level);
    
    @Query("SELECT eg FROM EmployeeGrade eg WHERE :salary BETWEEN eg.minSalary AND eg.maxSalary")
    List<EmployeeGrade> findBySalaryRange(@Param("salary") BigDecimal salary);

    // Fixed: Changed EmployeeGradeMstr to EmployeeGrade
    @Query("SELECT new map(eg.id as id, eg.name as name) FROM EmployeeGrade eg WHERE eg.active = true")
    List<Map<String, Object>> findIdAndNameForActive();
}