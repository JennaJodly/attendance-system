package com.example.attendance.repositories;

import com.example.attendance.entities.DepartmentMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface DepartmentMstrRepository extends JpaRepository<DepartmentMstr, Long> {
    
    @Query("SELECT d FROM DepartmentMstr d WHERE TRIM(LOWER(d.code)) = LOWER(:code)")
    Optional<DepartmentMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT d FROM DepartmentMstr d WHERE d.active = true")
    List<DepartmentMstr> findAllActive();

     @Query("SELECT d.id as id, d.name as name FROM DepartmentMstr d WHERE d.active = true")
    List<Map<String, Object>> findIdAndNameForActive();
    
    
}