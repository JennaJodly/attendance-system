package com.example.attendance.repositories;

import com.example.attendance.entities.StateMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StateMstrRepository extends JpaRepository<StateMstr, Long> {
    @Query("SELECT s FROM StateMstr s WHERE TRIM(LOWER(s.countryCode)) = LOWER(:countryCode)")
    List<StateMstr> findByCountryCodeFlexible(@Param("countryCode") String countryCode);
}


