package com.example.attendance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.attendance.entities.ComponentMaster;

import java.util.List;

public interface ComponentRepository extends JpaRepository<ComponentMaster, Long> {
    List<ComponentMaster> findByActiveTrue();
}
