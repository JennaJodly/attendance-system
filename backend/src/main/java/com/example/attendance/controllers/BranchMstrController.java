package com.example.attendance.controllers;

import com.example.attendance.entities.BranchMstr;
import com.example.attendance.repositories.BranchMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/branches")
public class BranchMstrController {

    @Autowired
    private BranchMstrRepository branchRepo;

    @GetMapping("/dropdown")
    public List<Map<String, Object>> getBranchesForDropdown() {
        return branchRepo.findIdAndNameForActive();
    }

    @GetMapping
    public List<BranchMstr> getAllBranches() {
        return branchRepo.findAll();
    }

    @GetMapping("/active")
    public List<BranchMstr> getActiveBranches() {
        return branchRepo.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchMstr> getBranchById(@PathVariable Long id) {
        Optional<BranchMstr> branch = branchRepo.findById(id);
        return branch.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-code")
    public ResponseEntity<BranchMstr> getBranchByCode(@RequestParam String code) {
        Optional<BranchMstr> branch = branchRepo.findByCode(code);
        return branch.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<BranchMstr> searchBranches(@RequestParam String name) {
        return branchRepo.findByNameContaining(name);
    }

    @PostMapping
    public BranchMstr createBranch(@RequestBody BranchMstr branch) {
        return branchRepo.save(branch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchMstr> updateBranch(@PathVariable Long id, @RequestBody BranchMstr branch) {
        if (!branchRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        branch.setId(id);
        return ResponseEntity.ok(branchRepo.save(branch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        if (!branchRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        branchRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}