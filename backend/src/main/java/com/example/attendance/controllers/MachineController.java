package com.example.attendance.controllers;

import com.example.attendance.entities.Machine;
import com.example.attendance.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin
public class MachineController {

    @Autowired
    private MachineRepository machineRepository;

    // POST: Add new machine
    @PostMapping
    public Machine addMachine(@RequestBody Machine machine) {
        return machineRepository.save(machine);
    }

    // GET: Get all active machines for dropdown
    @GetMapping("/dropdown")
    public List<Machine> getActiveMachines() {
        return machineRepository.findByActiveTrue();
    }

    // GET: Get all machines (optional)
    @GetMapping
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }
}
