package com.example.attendance.controllers;

import com.example.attendance.entities.DivisionMstr;
import com.example.attendance.repositories.DivisionMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/divisions")
public class DivisionMstrController {

    @Autowired
    private DivisionMstrRepository divisionRepo;
    
    @GetMapping("/dropdown")
    public List<Map<String, Object>> getDivisionsForDropdown() {
        return divisionRepo.findIdAndNameForActive();
    }
    
    @GetMapping
    public List<DivisionMstr> getAllDivisions() {
        return divisionRepo.findAll();
    }

    @GetMapping("/active")
    public List<DivisionMstr> getActiveDivisions() {
        return divisionRepo.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DivisionMstr> getDivisionById(@PathVariable Long id) {
        Optional<DivisionMstr> division = divisionRepo.findById(id);
        return division.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }



    @PostMapping
    public DivisionMstr createDivision(@RequestBody DivisionMstr division) {
        return divisionRepo.save(division);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DivisionMstr> updateDivision(@PathVariable Long id, @RequestBody DivisionMstr division) {
        if (!divisionRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        division.setId(id);
        return ResponseEntity.ok(divisionRepo.save(division));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDivision(@PathVariable Long id) {
        if (!divisionRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        divisionRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}