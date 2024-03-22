package edu.miu.cs.cs544.service;

import edu.miu.common.exception.ResourceNotFoundException;
import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.repository.AttendanceRepository;
import edu.miu.cs.cs544.repository.ScannerRepository;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class AttendanceServiceImpl extends BaseReadWriteServiceImpl<AttendancePayload, Attendance, Integer> implements AttendanceService {

    @Autowired
    private  AttendanceRepository attendanceRepository;
    @Autowired
    private ScannerRepository scannerRepository;
    @Override
    public List<Attendance> getAttendanceRecordsByScannerId(Integer scannerId) {
        return attendanceRepository.getAttendanceRecordsByScannerId(scannerId);
    }



    @Override
    public Attendance addAttendanceRecord(Integer scannerId, Attendance attendance) {
        // Use your mapper to map AttendancePayload to Attendance
        //Attendance attendance = attendancePayloadToAttendanceMapper.map(attendancePayload);

        // Retrieve the scanner object based on the provided scannerId
        Scanner scanner = scannerRepository.findById(scannerId)
                .orElseThrow(() -> new ResourceNotFoundException("Scanner not found with ID: " + scannerId));

        // Set the scanner in the attendance entity
        attendance.setScanner(scanner);

        // Save the attendance record using the repository
        return attendanceRepository.save(attendance);
    }

    @Override
    public Optional<Attendance> getAttendanceByIdAndScannerId(Integer attendanceId, Integer scannerId) {
        return attendanceRepository.findByAttendanceIdAndScannerId(attendanceId, scannerId);
    }

    @Override
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public boolean existsAttendanceByScannerIdAndId(Integer scannerId, Integer attendanceId) {
        return attendanceRepository.existsByScanner_IdAndAttendanceId(scannerId, attendanceId);
    }

    @Override
    public void deleteAttendanceByScannerIdAndId(Integer scannerId, Integer attendanceId) {
        attendanceRepository.deleteByScanner_IdAndAttendanceId(scannerId, attendanceId);
    }


}

