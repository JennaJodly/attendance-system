package com.example.attendance.controllers;

import com.example.attendance.entities.Attendance;
import com.example.attendance.entities.Employee;
import com.example.attendance.repositories.AttendanceRepository;
import com.example.attendance.repositories.EmployeeRepository;
import com.example.attendance.service.SmsService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // ✅ Mark IN
    @PostMapping("/mark-in")
    public ResponseEntity<?> markIn(@RequestBody Attendance attendance) {
        try {
            attendance.setActive(true);
            Attendance saved = attendanceRepository.save(attendance);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while marking IN");
        }
    }

    // ✅ Mark OUT
   @Autowired
private SmsService smsService;

// @PutMapping("/out/{employeeId}")
// public ResponseEntity<?> markAttendanceOut(
//     @PathVariable Long employeeId,
//     @RequestBody Map<String, Object> payload
// ) {
//     try {
//         String outTimeStr = (String) payload.get("outTime");
//         Integer durationWorked = (Integer) payload.get("durationWorked");

//         if (outTimeStr == null || durationWorked == null) {
//             return ResponseEntity.badRequest().body("Missing outTime or durationWorked");
//         }

//         Attendance attendance = attendanceRepository.findTopByEmployeeIdOrderByInDateDesc(employeeId);
//         if (attendance == null) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendance record not found");
//         }

//         LocalTime outTime = LocalTime.parse(outTimeStr);
//         attendance.setAttendanceOut(outTime);
//         attendance.setDurationWorked(durationWorked);
//         attendanceRepository.save(attendance);

//         // 🔔 Send SMS
//         // String employeePhone = "+919360673523";
//         String message = "Hi, your Attendance OUT has been marked at " + outTime + ". Duration worked: " + durationWorked + " minutes.";
//         String employeePhone = "+919360673523";

//         smsService.sendSms(employeePhone, message);

//         return ResponseEntity.ok("Attendance OUT updated and SMS sent successfully");

//     } catch (Exception e) {
//         e.printStackTrace();
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//             .body("Error updating OUT time");
//     }
// }
@Autowired
private EmployeeRepository employeeRepository;
@PutMapping("/out/{employeeId}")
public ResponseEntity<?> markAttendanceOut(
        @PathVariable Long employeeId,
        @RequestBody Map<String, Object> payload) {

    try {
        String outTimeStr = (String) payload.get("outTime");
        Integer durationWorked = (Integer) payload.get("durationWorked");

        if (outTimeStr == null || durationWorked == null) {
            return ResponseEntity.badRequest().body("Missing outTime or durationWorked");
        }

        Attendance attendance = attendanceRepository.findTopByEmployeeIdOrderByInDateDesc(employeeId);
        if (attendance == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendance record not found");
        }

        LocalTime outTime = LocalTime.parse(outTimeStr);
        attendance.setAttendanceOut(outTime);
        attendance.setDurationWorked(durationWorked);
        attendanceRepository.save(attendance);

        // ✅ Fetch employee and get official number
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty() || employeeOpt.get().getOfficialNo() == null) {
            return ResponseEntity.badRequest().body("Official phone number not found");
        }

        String phoneNumber = employeeOpt.get().getOfficialNo().trim();
        if (!phoneNumber.startsWith("+91")) {
            phoneNumber = "+91" + phoneNumber;
        }

        // ✅ Send SMS
        int hours = durationWorked / 60;
        int minutes = durationWorked % 60;

       String message = "Hi " + employeeOpt.get().getFirstName() + "," + "\n" +
                 "Your attendance OUT has been marked at " + outTime + "\n" +
                 "Duration worked: " + hours + "h " + minutes + "m.";
        smsService.sendSms(phoneNumber, message);



        return ResponseEntity.ok("Attendance OUT updated and SMS sent to " + phoneNumber);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating OUT time");
    }
}



    // ✅ Get Status
    @GetMapping("/status")
    public ResponseEntity<?> getAttendanceStatus(
        @RequestParam("employee_id") Long employeeId,
        @RequestParam("in_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inDate
    ) {
        try {
            Optional<Attendance> attendanceOpt = attendanceRepository
                .findTopByEmployeeIdAndInDate(employeeId, inDate);

            if (attendanceOpt.isPresent()) {
                Attendance attendance = attendanceOpt.get();

                Map<String, Object> response = new HashMap<>();
                response.put("inMarked", attendance.getInDate() != null);
                response.put("inTime", attendance.getAttendanceIn());
                response.put("outMarked", attendance.getAttendanceOut() != null);
                response.put("outTime", attendance.getAttendanceOut());
                response.put("attendanceId", attendance.getId());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.ok(Map.of(
                    "inMarked", false,
                    "inTime", null,
                    "outMarked", false,
                    "outTime", null,
                    "attendanceId", null
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching attendance status");
        }
    }
}

