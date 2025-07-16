
package com.example.attendance.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String fingerprintId;

    // Getters and Setters
}
