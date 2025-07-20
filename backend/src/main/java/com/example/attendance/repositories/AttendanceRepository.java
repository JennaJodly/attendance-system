package com.example.attendance.repositories;

import com.example.attendance.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findTopByEmployeeIdOrderByInDateDesc(Long employeeId);

    Optional<Attendance> findTopByEmployeeIdAndInDate(Long employeeId, LocalDate inDate); // ✅ FIXED
}
