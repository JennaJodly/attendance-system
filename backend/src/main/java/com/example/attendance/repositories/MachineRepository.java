package com.example.attendance.repositories;

import com.example.attendance.entities.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    List<Machine> findByActiveTrue();
}
