package com.example.attendance.controllers;

import com.example.attendance.entities.RegionMstr;
import com.example.attendance.repositories.RegionMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
public class RegionMstrController {

    @Autowired
    private RegionMstrRepository regionRepo;

    @GetMapping
    public List<RegionMstr> getAllRegions() {
        return regionRepo.findAll();
    }

    @GetMapping("/active")
    public List<RegionMstr> getActiveRegions() {
        return regionRepo.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionMstr> getRegionById(@PathVariable Long id) {
        Optional<RegionMstr> region = regionRepo.findById(id);
        return region.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-code")
    public ResponseEntity<RegionMstr> getRegionByCode(@RequestParam String code) {
        Optional<RegionMstr> region = regionRepo.findByCode(code);
        return region.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<RegionMstr> searchRegions(@RequestParam String name) {
        return regionRepo.findByNameContaining(name);
    }

    @PostMapping
    public RegionMstr createRegion(@RequestBody RegionMstr region) {
        return regionRepo.save(region);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionMstr> updateRegion(@PathVariable Long id, @RequestBody RegionMstr region) {
        if (!regionRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        region.setId(id);
        return ResponseEntity.ok(regionRepo.save(region));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        if (!regionRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        regionRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
