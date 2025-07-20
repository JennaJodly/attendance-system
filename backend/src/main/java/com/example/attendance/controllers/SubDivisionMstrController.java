package com.example.attendance.controllers;

import com.example.attendance.entities.SubDivisionMstr;
import com.example.attendance.repositories.SubDivisionMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sub-divisions")
public class SubDivisionMstrController {

    @Autowired
    private SubDivisionMstrRepository subDivisionRepo;

    @GetMapping
    public List<SubDivisionMstr> getAllSubDivisions() {
        return subDivisionRepo.findAll();
    }

    @GetMapping("/active")
    public List<SubDivisionMstr> getActiveSubDivisions() {
        return subDivisionRepo.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubDivisionMstr> getSubDivisionById(@PathVariable Long id) {
        Optional<SubDivisionMstr> subDivision = subDivisionRepo.findById(id);
        return subDivision.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-division/{divisionId}")
    public List<SubDivisionMstr> getSubDivisionsByDivision(@PathVariable Long divisionId) {
        return subDivisionRepo.findByDivisionId(divisionId);
    }

    @PostMapping
    public SubDivisionMstr createSubDivision(@RequestBody SubDivisionMstr subDivision) {
        return subDivisionRepo.save(subDivision);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubDivisionMstr> updateSubDivision(@PathVariable Long id, @RequestBody SubDivisionMstr subDivision) {
        if (!subDivisionRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subDivision.setId(id);
        return ResponseEntity.ok(subDivisionRepo.save(subDivision));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubDivision(@PathVariable Long id) {
        if (!subDivisionRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subDivisionRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}