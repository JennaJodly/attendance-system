package com.example.attendance.controllers;

import com.example.attendance.entities.Employee;
import com.example.attendance.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return employeeRepository.save(employee);
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

}
