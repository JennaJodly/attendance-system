package com.example.attendance.repositories;

import com.example.attendance.entities.BranchMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface BranchMstrRepository extends JpaRepository<BranchMstr, Long> {
    
    @Query("SELECT b FROM BranchMstr b WHERE TRIM(LOWER(b.code)) = LOWER(:code)")
    Optional<BranchMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT b FROM BranchMstr b WHERE b.active = true")
    List<BranchMstr> findAllActive();
    
    @Query("SELECT b FROM BranchMstr b WHERE TRIM(LOWER(b.name)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<BranchMstr> findByNameContaining(@Param("name") String name);
}
