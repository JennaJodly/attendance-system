package com.example.attendance.controllers;

import com.example.attendance.entities.CategoryMstr;
import com.example.attendance.repositories.CategoryMstrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryMstrController {

    @Autowired
    private CategoryMstrRepository categoryRepo;

    @GetMapping
    public List<CategoryMstr> getAllCategories() {
        return categoryRepo.findAll();
    }

    @GetMapping("/active")
    public List<CategoryMstr> getActiveCategories() {
        return categoryRepo.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryMstr> getCategoryById(@PathVariable Long id) {
        Optional<CategoryMstr> category = categoryRepo.findById(id);
        return category.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-code")
    public ResponseEntity<CategoryMstr> getCategoryByCode(@RequestParam String code) {
        Optional<CategoryMstr> category = categoryRepo.findByCode(code);
        return category.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<CategoryMstr> searchCategories(@RequestParam String name) {
        return categoryRepo.findByNameContaining(name);
    }

    @PostMapping
    public CategoryMstr createCategory(@RequestBody CategoryMstr category) {
        return categoryRepo.save(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryMstr> updateCategory(@PathVariable Long id, @RequestBody CategoryMstr category) {
        if (!categoryRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        category.setId(id);
        return ResponseEntity.ok(categoryRepo.save(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (!categoryRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}