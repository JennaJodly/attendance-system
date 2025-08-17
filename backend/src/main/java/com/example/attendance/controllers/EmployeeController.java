package com.example.attendance.controllers;

import com.example.attendance.entities.Employee;
import com.example.attendance.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin(origins = "*") // allow frontend calls
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

   @PostMapping("/add")
public Employee addEmployee(@RequestBody Employee employee) {
    // ✅ Link each document to the parent employee
    if (employee.getDocuments() != null) {
        employee.getDocuments().forEach(doc -> doc.setEmployee(employee));
    }

    // ✅ Link each bank detail to the parent employee
    if (employee.getBankDetails() != null) {
        employee.getBankDetails().forEach(bank -> bank.setEmployee(employee));
    }

    // ✅ Save employee; cascades to both documents and bank details
    return employeeRepository.save(employee);
}
@GetMapping("/fingerprints")
public List<Map<String, Object>> getEmployeeFingerprints() {
    List<Employee> employees = employeeRepository.findAll();

    List<Map<String, Object>> fingerprints = new ArrayList<>();

    for (Employee e : employees) {
        if (e.getFingerprint() != null && !e.getFingerprint().isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("fingerprint", e.getFingerprint());
            fingerprints.add(map);
        }
    }
    return fingerprints;
}


    @GetMapping("/all")
public List<Map<String, Object>> getAllEmployees() {
    List<Object[]> rows = employeeRepository.getAllJoinedEmployeesRaw();

    List<Map<String, Object>> result = new ArrayList<>();

    for (Object[] row : rows) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", row[0]);
        map.put("firstName", row[1]);
        map.put("lastName", row[2]);
        map.put("email", row[3]);
        map.put("empNo", row[4]);
        map.put("department", row[5]);
        map.put("subDepartment", row[6]);
        map.put("division", row[7]);
        map.put("designation", row[8]);
        map.put("region", row[9]);
        map.put("branch", row[10]); 
        map.put("officialNo", row[11]);
        map.put("photo", row[12]); 
        result.add(map);
    }

    return result;
}

 @GetMapping("/list")
    public List<Map<String, Object>> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(emp -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", emp.getId());
            dto.put("name", emp.getName());
            return dto;
        }).collect(Collectors.toList());
    }

}
