package com.example.attendance.controllers;

import com.example.attendance.entities.SubDivisionMstr;
import com.example.attendance.entities.DivisionMstr;
import com.example.attendance.repositories.SubDivisionMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sub-divisions")
public class SubDivisionMstrController {

   @Autowired
   private SubDivisionMstrRepository subDivisionRepo;

    @GetMapping("/dropdown")
    public List<Map<String, Object>> getSubDivisionsForDropdown() {
        return subDivisionRepo.findIdAndNameForActive();
    }
    
    @GetMapping("/dropdown/by-division/{divisionId}")
    public List<Map<String, Object>> getSubDivisionsByDivisionDropdown(@PathVariable Long divisionId) {
        // FIX: Use the correct method that returns Map format
        return subDivisionRepo.findIdAndNameByDivisionId(divisionId);
    }

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
       // FIX: Use the correct method name
       return subDivisionRepo.findByDivisionId(divisionId);
   }

   @PostMapping
   public ResponseEntity<?> createSubDivision(@RequestBody Map<String, Object> request) {
       try {
           SubDivisionMstr subDivision = new SubDivisionMstr();
           subDivision.setName((String) request.get("name"));
           subDivision.setCode((String) request.get("code"));
           subDivision.setDescription((String) request.get("description"));
           
           // Create division with just the ID
           DivisionMstr division = new DivisionMstr();
           division.setId(Long.valueOf(request.get("division_id").toString()));
           subDivision.setDivision(division);
           
           return ResponseEntity.ok(subDivisionRepo.save(subDivision));
       } catch (Exception e) {
           return ResponseEntity.status(500).body("Error: " + e.getMessage());
       }
   }

   @PutMapping("/{id}")
   public ResponseEntity<?> updateSubDivision(@PathVariable Long id, @RequestBody Map<String, Object> request) {
       try {
           if (!subDivisionRepo.existsById(id)) {
               return ResponseEntity.notFound().build();
           }
           
           SubDivisionMstr subDivision = new SubDivisionMstr();
           subDivision.setId(id);
           subDivision.setName((String) request.get("name"));
           subDivision.setCode((String) request.get("code"));
           subDivision.setDescription((String) request.get("description"));
           
           // Create division with just the ID
           DivisionMstr division = new DivisionMstr();
           division.setId(Long.valueOf(request.get("division_id").toString()));
           subDivision.setDivision(division);
           
           return ResponseEntity.ok(subDivisionRepo.save(subDivision));
       } catch (Exception e) {
           return ResponseEntity.status(500).body("Error: " + e.getMessage());
       }
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