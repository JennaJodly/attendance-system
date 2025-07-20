package com.example.attendance.repositories;

import com.example.attendance.entities.SubDepartmentMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface SubDepartmentMstrRepository extends JpaRepository<SubDepartmentMstr, Long> {
    
    @Query("SELECT sd FROM SubDepartmentMstr sd WHERE TRIM(LOWER(sd.code)) = LOWER(:code)")
    Optional<SubDepartmentMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT sd FROM SubDepartmentMstr sd WHERE sd.active = true")
    List<SubDepartmentMstr> findAllActive();
    
    @Query("SELECT sd FROM SubDepartmentMstr sd WHERE sd.department.id = :departmentId")
    List<SubDepartmentMstr> findByDepartmentId(@Param("departmentId") Long departmentId);
    
    @Query("SELECT new map(sd.id as id, sd.name as name, sd.department.id as departmentId) FROM SubDepartmentMstr sd WHERE sd.active = true")
    List<Map<String, Object>> findIdAndNameForActive();
    
    // Add this method that your controller is calling
    @Query("SELECT new map(sd.id as id, sd.name as name) FROM SubDepartmentMstr sd WHERE sd.active = true AND sd.department.id = :departmentId")
    List<Map<String, Object>> findIdAndNameByDepartmentId(@Param("departmentId") Long departmentId);
}