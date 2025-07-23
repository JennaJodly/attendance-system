package com.example.attendance.controllers;

import com.example.attendance.entities.DepartmentMstr;
import com.example.attendance.repositories.DepartmentMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentMstrController {

    @Autowired
    private DepartmentMstrRepository departmentRepo;
    private Long id;
    private String name;

    @GetMapping("/dropdown")
    public List<Map<String, Object>> getDepartmentsForDropdown() {
        return departmentRepo.findIdAndNameForActive();
    }

    @GetMapping
    public List<DepartmentMstr> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @GetMapping("/active")
    public List<DepartmentMstr> getActiveDepartments() {
        return departmentRepo.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentMstr> getDepartmentById(@PathVariable Long id) {
        Optional<DepartmentMstr> department = departmentRepo.findById(id);
        return department.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DepartmentMstr createDepartment(@RequestBody DepartmentMstr department) {
        return departmentRepo.save(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentMstr> updateDepartment(@PathVariable Long id, @RequestBody DepartmentMstr department) {
        if (!departmentRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        department.setId(id);
        return ResponseEntity.ok(departmentRepo.save(department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (!departmentRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        departmentRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

     public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}