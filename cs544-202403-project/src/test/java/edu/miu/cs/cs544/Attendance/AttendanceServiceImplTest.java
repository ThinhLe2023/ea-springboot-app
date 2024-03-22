package edu.miu.cs.cs544.Attendance;

import edu.miu.common.exception.ResourceNotFoundException;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.repository.AttendanceRepository;
import edu.miu.cs.cs544.repository.ScannerRepository;
import edu.miu.cs.cs544.service.AttendanceServiceImpl;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AttendanceServiceImplTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private ScannerRepository scannerRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAttendanceRecordsByScannerId() {
        // Mocking behavior of the repository method
        when(attendanceRepository.getAttendanceRecordsByScannerId(anyInt())).thenReturn(Collections.emptyList());

        // Call the service method
        List<Attendance> attendanceList = attendanceService.getAttendanceRecordsByScannerId(1);

        // Verify that the repository method was called with the correct argument
        verify(attendanceRepository).getAttendanceRecordsByScannerId(eq(1));

        // Validate the result
        assertNotNull(attendanceList);
        assertTrue(attendanceList.isEmpty());
    }

    @Test
    public void testAddAttendanceRecord_Success() {
        Integer scannerId = 1;
        Attendance attendance = new Attendance();
        Scanner scanner = new Scanner();
        scanner.setId(scannerId);

        // Mocking behavior of the repository methods
        when(scannerRepository.findById(scannerId)).thenReturn(Optional.of(scanner));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        // Call the service method
        Attendance addedAttendance = attendanceService.addAttendanceRecord(scannerId, attendance);

        // Verify that the repository methods were called with the correct arguments
        verify(scannerRepository).findById(eq(scannerId));
        verify(attendanceRepository).save(eq(attendance));

        // Validate the result
        assertNotNull(addedAttendance);
        assertEquals(scannerId, addedAttendance.getScanner().getId());
    }

    @Test
    public void testAddAttendanceRecord_ScannerNotFound() {
        Integer scannerId = 1;
        Attendance attendance = new Attendance();

        // Mocking behavior of the repository methods to simulate Scanner not found
        when(scannerRepository.findById(scannerId)).thenReturn(Optional.empty());

        // Call the service method and expect ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class,
                () -> attendanceService.addAttendanceRecord(scannerId, attendance));

        // Verify that the repository method was called with the correct argument
        verify(scannerRepository).findById(eq(scannerId));
        // Make sure that save method was not called since Scanner was not found
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    // Add more test methods as needed for other service methods
}