package com.example.attendance.repositories;

import com.example.attendance.entities.SubDivisionMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface SubDivisionMstrRepository extends JpaRepository<SubDivisionMstr, Long> {
    
    @Query("SELECT sdiv FROM SubDivisionMstr sdiv WHERE TRIM(LOWER(sdiv.code)) = LOWER(:code)")
    Optional<SubDivisionMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT sdiv FROM SubDivisionMstr sdiv WHERE sdiv.active = true")
    List<SubDivisionMstr> findAllActive();
    
    @Query("SELECT sdiv FROM SubDivisionMstr sdiv WHERE sdiv.division.id = :divisionId")
    List<SubDivisionMstr> findByDivisionId(@Param("divisionId") Long divisionId);
   
    @Query("SELECT new map(sd.id as id, sd.name as name, sd.division.id as divisionId) FROM SubDivisionMstr sd WHERE sd.active = true")
    List<Map<String, Object>> findIdAndNameForActive();
    
    // @Query("SELECT new map(sd.id as id, sd.name as name) FROM SubDivisionMstr sd WHERE sd.active = true AND sd.division.id = :divisionId")
    // List<Map<String, Object>> findByDivisionId(@Param("divisionId") Long divisionId);


    @Query("SELECT new map(s.id as id, s.name as name) FROM SubDivisionMstr s WHERE s.division.id = :divisionId AND s.active = true")
    List<Map<String, Object>> findIdAndNameByDivisionId(@Param("divisionId") Long divisionId);

}