package com.example.attendance.controllers;

import com.example.attendance.entities.SubDepartmentMstr;
import com.example.attendance.repositories.SubDepartmentMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sub-departments")
public class SubDepartmentMstrController {

    @Autowired
    private SubDepartmentMstrRepository subDepartmentRepo;

    @GetMapping
    public List<SubDepartmentMstr> getAllSubDepartments() {
        return subDepartmentRepo.findAll();
    }

    @GetMapping("/active")
    public List<SubDepartmentMstr> getActiveSubDepartments() {
        return subDepartmentRepo.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubDepartmentMstr> getSubDepartmentById(@PathVariable Long id) {
        Optional<SubDepartmentMstr> subDepartment = subDepartmentRepo.findById(id);
        return subDepartment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-department/{departmentId}")
    public List<SubDepartmentMstr> getSubDepartmentsByDepartment(@PathVariable Long departmentId) {
        return subDepartmentRepo.findByDepartmentId(departmentId);
    }

    @PostMapping
    public SubDepartmentMstr createSubDepartment(@RequestBody SubDepartmentMstr subDepartment) {
        return subDepartmentRepo.save(subDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubDepartmentMstr> updateSubDepartment(@PathVariable Long id, @RequestBody SubDepartmentMstr subDepartment) {
        if (!subDepartmentRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subDepartment.setId(id);
        return ResponseEntity.ok(subDepartmentRepo.save(subDepartment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubDepartment(@PathVariable Long id) {
        if (!subDepartmentRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subDepartmentRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}