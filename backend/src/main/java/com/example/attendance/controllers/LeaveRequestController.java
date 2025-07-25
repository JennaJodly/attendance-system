package com.example.attendance.controllers;

import com.example.attendance.entities.LeaveRequest;
import com.example.attendance.repositories.LeaveRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

// @CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/leave_request")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @PostMapping("/ask_leave")
    public LeaveRequest askLeave(@RequestBody LeaveRequest leaveRequest) {
        leaveRequest.setCreatedOn(LocalDateTime.now());
        return leaveRequestRepository.save(leaveRequest);
    }
}
