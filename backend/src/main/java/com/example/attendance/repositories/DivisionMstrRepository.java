package com.example.attendance.repositories;

import com.example.attendance.entities.DivisionMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface DivisionMstrRepository extends JpaRepository<DivisionMstr, Long> {
    
    @Query("SELECT div FROM DivisionMstr div WHERE TRIM(LOWER(div.code)) = LOWER(:code)")
    Optional<DivisionMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT div FROM DivisionMstr div WHERE div.active = true")
    List<DivisionMstr> findAllActive();
   
    
    @Query("SELECT new map(d.id as id, d.name as name) FROM DivisionMstr d WHERE d.active = true")
    List<Map<String, Object>> findIdAndNameForActive();
}