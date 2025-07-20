package com.example.attendance.repositories;

import com.example.attendance.entities.SubDivisionMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SubDivisionMstrRepository extends JpaRepository<SubDivisionMstr, Long> {
    
    @Query("SELECT sdiv FROM SubDivisionMstr sdiv WHERE TRIM(LOWER(sdiv.code)) = LOWER(:code)")
    Optional<SubDivisionMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT sdiv FROM SubDivisionMstr sdiv WHERE sdiv.active = true")
    List<SubDivisionMstr> findAllActive();
    
    @Query("SELECT sdiv FROM SubDivisionMstr sdiv WHERE sdiv.division.id = :divisionId")
    List<SubDivisionMstr> findByDivisionId(@Param("divisionId") Long divisionId);
}