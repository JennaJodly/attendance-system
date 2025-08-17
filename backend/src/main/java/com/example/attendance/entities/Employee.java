package com.example.attendance.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    @Column(name = "emp_no", length = 20, unique = true)
    private String empNo;
    @Column(columnDefinition = "TEXT")
    private String address;
    private String countryCode;
    private String stateCode;
    private String cityCode;
    private String officialNo;
    private String whatsappNo;
    private LocalDate bdayDate;
    private LocalDate dateOfJoining;
    private String gender;
    private String bloodGroup;
    private String emergencyContactNo;
    @Column(columnDefinition = "TEXT")
    private String photo;
    @Column(columnDefinition = "TEXT") // Stores Base64 fingerprint template
    private String fingerprint;
    private Integer branch;
    private Integer department;
    private Integer subDepartment;
    private Integer designation;
    private Integer region;
    private Integer division;
    private Integer subDivision;
    private Integer category;
    private Integer empGrade;
    private Integer reportingTo;
    private Integer noOfApp;
    private Integer appraiser1;
    private Integer appraiser2;
    private Integer reviewer;
    private LocalDate effFrom;
    private String role;
    private Boolean active;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeDocument> documents;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeBank> bankDetails;
}


