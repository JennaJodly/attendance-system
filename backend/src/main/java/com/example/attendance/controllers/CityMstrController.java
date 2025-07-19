package com.example.attendance.controllers;

import com.example.attendance.entities.CityMstr;
import com.example.attendance.repositories.CityMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityMstrController {

    @Autowired
    private CityMstrRepository cityRepo;

    @GetMapping("/by-country-and-state")
    public List<CityMstr> getCitiesByCountryAndState(@RequestParam String countryCode, @RequestParam String stateCode) {
        return cityRepo.findCitiesByCountryAndState(countryCode, stateCode);  // 🔄 updated method call
    }
}
