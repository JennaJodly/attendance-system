package com.example.attendance.controllers;

import com.example.attendance.entities.DesignationMstr;
import com.example.attendance.repositories.DesignationMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/designations")
public class DesignationMstrController {

    @Autowired
    private DesignationMstrRepository designationRepo;

   @GetMapping("/dropdown")
    public List<Map<String, Object>> getDesignationsForDropdown() {
        return designationRepo.findIdAndNameForActive();
    }
    
    @GetMapping
    public List<DesignationMstr> getAllDesignations() {
        return designationRepo.findAll();
    }

    @GetMapping("/active")
    public List<DesignationMstr> getActiveDesignations() {
        return designationRepo.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DesignationMstr> getDesignationById(@PathVariable Long id) {
        Optional<DesignationMstr> designation = designationRepo.findById(id);
        return designation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-level/{level}")
    public List<DesignationMstr> getDesignationsByLevel(@PathVariable Integer level) {
        return designationRepo.findByLevel(level);
    }

    @PostMapping
    public DesignationMstr createDesignation(@RequestBody DesignationMstr designation) {
        return designationRepo.save(designation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DesignationMstr> updateDesignation(@PathVariable Long id, @RequestBody DesignationMstr designation) {
        if (!designationRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        designation.setId(id);
        return ResponseEntity.ok(designationRepo.save(designation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long id) {
        if (!designationRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        designationRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
