package com.example.attendance.controllers;

import com.example.attendance.entities.EmployeeGrade;
import com.example.attendance.repositories.EmployeeGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/employee-grades")
public class EmployeeGradeController {

    @Autowired
    private EmployeeGradeRepository employeeGradeRepo;

    @GetMapping
    public List<EmployeeGrade> getAllEmployeeGrades() {
        return employeeGradeRepo.findAll();
    }

    @GetMapping("/active")
    public List<EmployeeGrade> getActiveEmployeeGrades() {
        return employeeGradeRepo.findAllActiveOrderByLevel();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeGrade> getEmployeeGradeById(@PathVariable Long id) {
        Optional<EmployeeGrade> employeeGrade = employeeGradeRepo.findById(id);
        return employeeGrade.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-code")
    public ResponseEntity<EmployeeGrade> getEmployeeGradeByCode(@RequestParam String code) {
        Optional<EmployeeGrade> employeeGrade = employeeGradeRepo.findByCode(code);
        return employeeGrade.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-level/{level}")
    public List<EmployeeGrade> getEmployeeGradesByLevel(@PathVariable Integer level) {
        return employeeGradeRepo.findByLevel(level);
    }

    @GetMapping("/by-salary-range")
    public List<EmployeeGrade> getEmployeeGradesBySalary(@RequestParam BigDecimal salary) {
        return employeeGradeRepo.findBySalaryRange(salary);
    }

    @PostMapping
    public EmployeeGrade createEmployeeGrade(@RequestBody EmployeeGrade employeeGrade) {
        return employeeGradeRepo.save(employeeGrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeGrade> updateEmployeeGrade(@PathVariable Long id, @RequestBody EmployeeGrade employeeGrade) {
        if (!employeeGradeRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        employeeGrade.setId(id);
        return ResponseEntity.ok(employeeGradeRepo.save(employeeGrade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeGrade(@PathVariable Long id) {
        if (!employeeGradeRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        employeeGradeRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}