package edu.miu.cs.cs544;

import edu.miu.cs.cs544.controller.ScannerController;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.service.AttendanceService;
import edu.miu.cs.cs544.service.ScannerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScannerControllerTest {
    @Mock
    private AttendanceService attendanceService;
    @Mock
    private ScannerService scannerService;

    @InjectMocks
    private ScannerController scannerController;

    public ScannerControllerTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAttendanceRecordsByScannerId() {
        int scannerId = 1;
        List<Attendance> attendanceList = new ArrayList<>();
        // Add sample attendance records to the list

        when(attendanceService.getAttendanceRecordsByScannerId(scannerId)).thenReturn(attendanceList);

        List<Attendance> responseList = scannerController.getAttendanceRecordsByScannerId(scannerId);

        verify(attendanceService, times(1)).getAttendanceRecordsByScannerId(scannerId);
        assertEquals(attendanceList.size(), responseList.size());
    }

    @Test
    public void testAddAttendanceRecord() {
        int scannerId = 1;
        Attendance attendance = new Attendance();
        // Set attendance properties

        Scanner scanner = new Scanner();
        // Set scanner properties
        when(scannerService.getScannerById(scannerId)).thenReturn(scanner);
        when(attendanceService.addAttendanceRecord(scannerId, attendance)).thenReturn(attendance);

        ResponseEntity<?> responseEntity = scannerController.addAttendanceRecord(scannerId, attendance);

        verify(scannerService, times(1)).getScannerById(scannerId);
        verify(attendanceService, times(1)).addAttendanceRecord(scannerId, attendance);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateAttendanceRecord() {
        int scannerId = 1;
        int attendanceId = 1;
        Attendance attendance = new Attendance();
        // Set attendance properties

        Optional<Attendance> existingAttendance = Optional.of(new Attendance());
        // Set properties of the existing attendance record

        when(attendanceService.getAttendanceByIdAndScannerId(attendanceId, scannerId)).thenReturn(existingAttendance);
        when(attendanceService.saveAttendance(any(Attendance.class))).thenReturn(attendance);

        ResponseEntity<Attendance> responseEntity = scannerController.updateAttendanceRecord(scannerId, attendanceId, attendance);

        verify(attendanceService, times(1)).getAttendanceByIdAndScannerId(attendanceId, scannerId);
        verify(attendanceService, times(1)).saveAttendance(any(Attendance.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void testDeleteAttendanceRecord_Success() {
        int scannerId = 1;
        int attendanceId = 1;

        // Mocking the existence check and deletion operation
        when(attendanceService.existsAttendanceByScannerIdAndId(scannerId, attendanceId)).thenReturn(true);
        doNothing().when(attendanceService).deleteAttendanceByScannerIdAndId(scannerId, attendanceId);

        // Call the deleteAttendanceRecord method
        ResponseEntity<String> responseEntity = scannerController.deleteAttendanceRecord(scannerId, attendanceId);

        // Verify that the service methods were called and the response status is NO_CONTENT
        verify(attendanceService, times(1)).existsAttendanceByScannerIdAndId(scannerId, attendanceId);
        verify(attendanceService, times(1)).deleteAttendanceByScannerIdAndId(scannerId, attendanceId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteAttendanceRecord_NotFound() {
        int scannerId = 1;
        int attendanceId = 1;

        // Mocking the existence check to return false
        when(attendanceService.existsAttendanceByScannerIdAndId(anyInt(), anyInt())).thenReturn(false);

        // Call the deleteAttendanceRecord method
        ResponseEntity<String> responseEntity = scannerController.deleteAttendanceRecord(scannerId, attendanceId);

        // Verify that the service method was called and the response status is NOT_FOUND
        verify(attendanceService, times(1)).existsAttendanceByScannerIdAndId(scannerId, attendanceId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
