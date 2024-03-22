package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Attendance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.service.AttendanceService;
import edu.miu.cs.cs544.service.ScannerService;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.contract.ScannerPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scanners")
@RequiredArgsConstructor
public class ScannerController extends BaseReadWriteController<ScannerPayload, Scanner, Integer> {

   private final AttendanceService attendanceService;
    private final ScannerService scannerService;

    @GetMapping("/{id}/record")
    public List<Attendance> getAttendanceRecordsByScannerId(@PathVariable("id") Integer scannerId) {
        return attendanceService.getAttendanceRecordsByScannerId(scannerId);
    }

    @PostMapping("/{scannerId}/record")
    public ResponseEntity<?> addAttendanceRecord(@PathVariable("scannerId") Integer scannerId,
                                                 @RequestBody Attendance attendance) {
        // Fetch the scanner from the scanner service
        Scanner scanner = scannerService.getScannerById(scannerId);

        if (scanner == null) {
            return new ResponseEntity<>("Scanner not found", HttpStatus.NOT_FOUND);
        }

        // Set the scanner in the attendance object
        attendance.setScanner(scanner);

        // Automatically set scannedAt to the current timestamp
        attendance.setScannedAt(LocalDateTime.now());

        // Add the attendance record using the attendance service
        Attendance addedAttendance = attendanceService.addAttendanceRecord(scannerId, attendance);

        return new ResponseEntity<>(addedAttendance, HttpStatus.CREATED);
    }

    @PutMapping("/{scannerId}/record/{attendanceId}")
    public ResponseEntity<Attendance> updateAttendanceRecord(
            @PathVariable("scannerId") Integer scannerId,
            @PathVariable("attendanceId") Integer attendanceId,
            @RequestBody Attendance attendance) {

        Optional<Attendance> existingAttendance = attendanceService.getAttendanceByIdAndScannerId(attendanceId, scannerId);
        if (existingAttendance.isPresent()) {
            Attendance updatedAttendance = existingAttendance.get();
            updatedAttendance.setMember(attendance.getMember());
            updatedAttendance.setSession(attendance.getSession());
            updatedAttendance.setScannedAt(attendance.getScannedAt());
            updatedAttendance.setPresent(attendance.isPresent());

            Attendance savedAttendance = attendanceService.saveAttendance(updatedAttendance);
            return new ResponseEntity<>(savedAttendance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{scannerId}/record/{attendanceId}")
    public ResponseEntity<String> deleteAttendanceRecord(
            @PathVariable("scannerId") Integer scannerId,
            @PathVariable("attendanceId") Integer attendanceId) {

        // Check if the attendance exists for the given scanner and ID
        if (!attendanceService.existsAttendanceByScannerIdAndId(scannerId, attendanceId)) {
            return ResponseEntity.notFound().build();
        }

        // Delete the attendance record
        attendanceService.deleteAttendanceByScannerIdAndId(scannerId, attendanceId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    /*@PostMapping("/{scannerId}/record")
    public ResponseEntity<Attendance> addAttendanceRecord(@PathVariable("scannerId") Integer scannerId
                                                          ) {
        //@RequestBody Attendance attendance
        // Create a new Attendance object
        Attendance attendance = new Attendance();

        // Set the scanner ID in the attendance object directly
        Scanner scanner = scannerService.getScannerById(scannerId);
        if (scanner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        attendance.setScanner(scanner);

        // Add the attendance record using the attendance service
        Attendance addedAttendance = attendanceService.addAttendanceRecord(scannerId, attendance);

        return new ResponseEntity<>(addedAttendance, HttpStatus.CREATED);
    }*/

   /* @GetMapping("{scannerId}/records")
    public  List<Attendance> getTotalEventAttendanace(@PathVariable("scannerId") Integer scannerId){

        return scannerService.getAttendanceRecordsByScannerId(scannerId);
    }*/
}
