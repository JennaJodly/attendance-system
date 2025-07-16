
package com.example.attendance.controllers;

import com.example.attendance.entities.Attendance;
import com.example.attendance.entities.Employee;
import com.example.attendance.repositories.AttendanceRepository;
import com.example.attendance.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @PostMapping("/punch")
    public String punch(@RequestParam String fingerprintId, @RequestParam String type) {
        Optional<Employee> employeeOpt = employeeRepo.findByFingerprintId(fingerprintId);
        if (employeeOpt.isPresent()) {
            Attendance record = new Attendance();
            record.setEmployee(employeeOpt.get());
            record.setTimestamp(LocalDateTime.now());
            record.setType(type);
            record.setPhotoPath("/photos/sample.jpg");
            attendanceRepo.save(record);
            return "Punch recorded: " + type;
        } else {
            return "Employee not found";
        }
    }
}
