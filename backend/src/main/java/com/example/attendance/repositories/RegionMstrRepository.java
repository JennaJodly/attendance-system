package com.example.attendance.repositories;

import com.example.attendance.entities.RegionMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface RegionMstrRepository extends JpaRepository<RegionMstr, Long> {
    
    @Query("SELECT r FROM RegionMstr r WHERE TRIM(LOWER(r.code)) = LOWER(:code)")
    Optional<RegionMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT r FROM RegionMstr r WHERE r.active = true")
    List<RegionMstr> findAllActive();
    
    @Query("SELECT r FROM RegionMstr r WHERE TRIM(LOWER(r.name)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<RegionMstr> findByNameContaining(@Param("name") String name);
 
    @Query("SELECT new map(r.id as id, r.name as name) FROM RegionMstr r WHERE r.active = true")
    List<Map<String, Object>> findIdAndNameForActive();


}