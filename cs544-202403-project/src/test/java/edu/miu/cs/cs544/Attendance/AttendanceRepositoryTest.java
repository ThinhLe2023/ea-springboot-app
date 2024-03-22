package edu.miu.cs.cs544.Attendance;

import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.repository.AttendanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AttendanceRepositoryTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Test
    public void testSaveAttendance() {
        // Create a sample attendance
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(1);
        attendance.setScanner(new Scanner());
        attendance.setMember(new Member());
        attendance.setSession(new Session());
        attendance.setPresent(true);
        attendance.setScannedAt(LocalDateTime.now());

        // Mock behavior of save method
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        // Perform save operation
        Attendance savedAttendance = attendanceRepository.save(attendance);

        // Verify that save method was called with the correct argument
        verify(attendanceRepository).save(eq(attendance));

        // Validate the saved attendance
        assertEquals(attendance.getAttendanceId(), savedAttendance.getAttendanceId());
        assertEquals(attendance.getScanner(), savedAttendance.getScanner());
        assertEquals(attendance.getMember(), savedAttendance.getMember());
        assertEquals(attendance.getSession(), savedAttendance.getSession());
        assertEquals(attendance.isPresent(), savedAttendance.isPresent());
        assertEquals(attendance.getScannedAt(), savedAttendance.getScannedAt());
    }

    @Test
    public void testFindAttendanceById() {
        // Sample attendance ID
        Integer attendanceId = 1;

        // Create a sample attendance
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(attendanceId);
        attendance.setScanner(new Scanner());
        attendance.setMember(new Member());
        attendance.setSession(new Session());
        attendance.setPresent(true);
        attendance.setScannedAt(LocalDateTime.now());

        // Mock behavior of findById method
        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));

        // Perform findById operation
        Optional<Attendance> foundAttendanceOptional = attendanceRepository.findById(attendanceId);

        // Verify that findById method was called with the correct argument
        verify(attendanceRepository).findById(eq(attendanceId));

        // Validate the found attendance
        assertEquals(attendanceId, foundAttendanceOptional.get().getAttendanceId());
        assertEquals(attendance.getScanner(), foundAttendanceOptional.get().getScanner());
        assertEquals(attendance.getMember(), foundAttendanceOptional.get().getMember());
        assertEquals(attendance.getSession(), foundAttendanceOptional.get().getSession());
        assertEquals(attendance.isPresent(), foundAttendanceOptional.get().isPresent());
        assertEquals(attendance.getScannedAt(), foundAttendanceOptional.get().getScannedAt());
    }

    @Test
    public void testDeleteAttendance() {
        // Sample attendance ID
        Integer attendanceId = 1;

        // Perform deleteById operation
        attendanceRepository.deleteById(attendanceId);

        // Verify that deleteById method was called with the correct argument
        verify(attendanceRepository).deleteById(eq(attendanceId));
    }

}