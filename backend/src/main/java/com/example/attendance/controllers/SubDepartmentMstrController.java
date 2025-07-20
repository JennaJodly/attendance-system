package com.example.attendance.controllers;

import com.example.attendance.entities.SubDepartmentMstr;
import com.example.attendance.entities.DepartmentMstr;
import com.example.attendance.repositories.SubDepartmentMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sub-departments")
public class SubDepartmentMstrController {

   @Autowired
   private SubDepartmentMstrRepository subDepartmentRepo;

   @GetMapping("/dropdown")
    public List<Map<String, Object>> getSubDepartmentsForDropdown() {
        return subDepartmentRepo.findIdAndNameForActive();
    }
    
    @GetMapping("/dropdown/by-department/{departmentId}")
    public List<Map<String, Object>> getSubDepartmentsByDepartmentDropdown(@PathVariable Long departmentId) {
        // FIX: Use the correct method that returns Map format
        return subDepartmentRepo.findIdAndNameByDepartmentId(departmentId);
    }

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
       // FIX: Use the correct method name
       return subDepartmentRepo.findByDepartmentId(departmentId);
   }

   @PostMapping
   public ResponseEntity<?> createSubDepartment(@RequestBody Map<String, Object> request) {
       try {
           SubDepartmentMstr subDepartment = new SubDepartmentMstr();
           subDepartment.setName((String) request.get("name"));
           subDepartment.setCode((String) request.get("code"));
           subDepartment.setDescription((String) request.get("description"));
           
           // Create department with just the ID
           DepartmentMstr department = new DepartmentMstr();
           department.setId(Long.valueOf(request.get("department_id").toString()));
           subDepartment.setDepartment(department);
           
           return ResponseEntity.ok(subDepartmentRepo.save(subDepartment));
       } catch (Exception e) {
           return ResponseEntity.status(500).body("Error: " + e.getMessage());
       }
   }

   @PutMapping("/{id}")
   public ResponseEntity<?> updateSubDepartment(@PathVariable Long id, @RequestBody Map<String, Object> request) {
       try {
           if (!subDepartmentRepo.existsById(id)) {
               return ResponseEntity.notFound().build();
           }
           
           SubDepartmentMstr subDepartment = new SubDepartmentMstr();
           subDepartment.setId(id);
           subDepartment.setName((String) request.get("name"));
           subDepartment.setCode((String) request.get("code"));
           subDepartment.setDescription((String) request.get("description"));
           
           // Create department with just the ID
           DepartmentMstr department = new DepartmentMstr();
           department.setId(Long.valueOf(request.get("department_id").toString()));
           subDepartment.setDepartment(department);
           
           return ResponseEntity.ok(subDepartmentRepo.save(subDepartment));
       } catch (Exception e) {
           return ResponseEntity.status(500).body("Error: " + e.getMessage());
       }
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