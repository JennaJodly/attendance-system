package com.example.attendance.controllers;
import com.example.attendance.entities.ComponentMaster;
import com.example.attendance.repositories.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
@CrossOrigin
public class ComponentController {

    @Autowired
    private ComponentRepository componentRepository;

    // POST: Add new component
    @PostMapping
    public ComponentMaster addComponent(@RequestBody ComponentMaster component) {
        component.setActive(true); // ensure it's active by default
        return componentRepository.save(component);
    }

    // GET: Get all active components for dropdown
    @GetMapping("/dropdown")
    public List<ComponentMaster> getActiveComponents() {
        return componentRepository.findByActiveTrue();
    }

    // GET: Get all components (optional)
    @GetMapping
    public List<ComponentMaster> getAllComponents() {
        return componentRepository.findAll();
    }
}
