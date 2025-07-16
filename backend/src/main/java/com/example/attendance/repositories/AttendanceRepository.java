
package com.example.attendance.repositories;

import com.example.attendance.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
}
