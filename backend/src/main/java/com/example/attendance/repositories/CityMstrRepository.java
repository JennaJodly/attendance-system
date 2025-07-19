package com.example.attendance.repositories;

import com.example.attendance.entities.CityMstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CityMstrRepository extends JpaRepository<CityMstr, Long> {

    @Query("SELECT c FROM CityMstr c WHERE TRIM(LOWER(c.countryCode)) = LOWER(:countryCode) AND TRIM(c.stateCode) = :stateCode")
    List<CityMstr> findCitiesByCountryAndState(@Param("countryCode") String countryCode, @Param("stateCode") String stateCode);
}
