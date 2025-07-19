package com.example.attendance.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "statemstr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateMstr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // New primary key

    @Column(length = 10)
    private String code; // Now allows duplicates

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "dial_code")
    private String dialCode;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
