package com.example.attendance.repositories;

import com.example.attendance.entities.SubDepartmentMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SubDepartmentMstrRepository extends JpaRepository<SubDepartmentMstr, Long> {
    
    @Query("SELECT sd FROM SubDepartmentMstr sd WHERE TRIM(LOWER(sd.code)) = LOWER(:code)")
    Optional<SubDepartmentMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT sd FROM SubDepartmentMstr sd WHERE sd.active = true")
    List<SubDepartmentMstr> findAllActive();
    
    @Query("SELECT sd FROM SubDepartmentMstr sd WHERE sd.department.id = :departmentId")
    List<SubDepartmentMstr> findByDepartmentId(@Param("departmentId") Long departmentId);
}