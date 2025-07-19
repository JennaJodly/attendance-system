package com.example.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.attendance.entities.countrymstr;

import com.example.attendance.repositories.CountrymstrRepository;

@RestController
@RequestMapping("/api/countries")
public class CountryMstrController {
    
    @Autowired
    private CountrymstrRepository countryRepo;

    @GetMapping
    public List<countrymstr> getAllCountries() {
        return countryRepo.findAll();
    }
}
