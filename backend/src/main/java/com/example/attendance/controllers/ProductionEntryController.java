package com.example.attendance.controllers;

import com.example.attendance.entities.ProductionEntry;
import com.example.attendance.repositories.ProductionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/production-entries")
@CrossOrigin
public class ProductionEntryController {

    @Autowired
    private ProductionEntryRepository entryRepo;

    @PostMapping
    public ProductionEntry saveEntry(@RequestBody ProductionEntry entry) {
        entry.setInsertedAt(LocalDateTime.now());
        return entryRepo.save(entry);
    }

    @GetMapping
    public List<ProductionEntry> getAllEntries() {
        return entryRepo.findAll();
    }
}
