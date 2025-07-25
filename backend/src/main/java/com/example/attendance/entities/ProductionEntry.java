package com.example.attendance.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "production_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to Employee
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // Foreign key to Component
    @ManyToOne
    @JoinColumn(name = "component_id")
    private ComponentMaster component;

    // Foreign key to Machine
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    private Integer count;

    private LocalDate date;

    private String shift;

    private LocalDateTime insertedAt;
}
