package com.example.attendance.repositories;

import com.example.attendance.entities.DesignationMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface DesignationMstrRepository extends JpaRepository<DesignationMstr, Long> {
    
    @Query("SELECT des FROM DesignationMstr des WHERE TRIM(LOWER(des.code)) = LOWER(:code)")
    Optional<DesignationMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT des FROM DesignationMstr des WHERE des.active = true")
    List<DesignationMstr> findAllActive();
    
    @Query("SELECT des FROM DesignationMstr des WHERE des.level = :level")
    List<DesignationMstr> findByLevel(@Param("level") Integer level);

    @Query("SELECT new map(d.id as id, d.name as name) FROM DesignationMstr d WHERE d.active = true")
    List<Map<String, Object>> findIdAndNameForActive();

}
