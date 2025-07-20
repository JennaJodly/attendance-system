package com.example.attendance.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Boolean active;

    private LocalDate inDate;

    private LocalTime attendanceIn;

    private LocalTime attendanceOut;

    private Integer durationWorked; // in minutes

    private String shift;

    private String photoPath;

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getInDate() {
        return inDate;
    }

    public void setInDate(LocalDate inDate) {
        this.inDate = inDate;
    }

    public LocalTime getAttendanceIn() {
        return attendanceIn;
    }

    public void setAttendanceIn(LocalTime attendanceIn) {
        this.attendanceIn = attendanceIn;
    }

    public LocalTime getAttendanceOut() {
        return attendanceOut;
    }

    public void setAttendanceOut(LocalTime attendanceOut) {
        this.attendanceOut = attendanceOut;
    }

    public Integer getDurationWorked() {
        return durationWorked;
    }

    public void setDurationWorked(Integer durationWorked) {
        this.durationWorked = durationWorked;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    // public Object getOutTime() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getOutTime'");
    // }

    // public Object getStatus() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
    // }

    // Remove unused/unimplemented methods like getOutTime(), getStatus()
}
