package com.example.attendance.repositories;

import com.example.attendance.entities.CategoryMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface CategoryMstrRepository extends JpaRepository<CategoryMstr, Long> {
    
    @Query("SELECT c FROM CategoryMstr c WHERE TRIM(LOWER(c.code)) = LOWER(:code)")
    Optional<CategoryMstr> findByCode(@Param("code") String code);
    
    @Query("SELECT c FROM CategoryMstr c WHERE c.active = true")
    List<CategoryMstr> findAllActive();
    
    @Query("SELECT c FROM CategoryMstr c WHERE TRIM(LOWER(c.name)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CategoryMstr> findByNameContaining(@Param("name") String name);

   @Query("SELECT new map(c.id as id, c.name as name) FROM CategoryMstr c WHERE c.active = true")
    List<Map<String, Object>> findIdAndNameForActive();

}