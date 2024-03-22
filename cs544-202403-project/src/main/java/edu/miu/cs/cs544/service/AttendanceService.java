package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.service.contract.AttendancePayload;

import java.util.List;
import java.util.Optional;

public interface AttendanceService extends BaseReadWriteService<AttendancePayload, Attendance, Integer> {
    List<Attendance> getAttendanceRecordsByScannerId(Integer scannerId);
    Attendance addAttendanceRecord(Integer scannerId, Attendance attendance);
    public Optional<Attendance> getAttendanceByIdAndScannerId(Integer attendanceId, Integer scannerId);
    public Attendance saveAttendance(Attendance attendance);
    public boolean existsAttendanceByScannerIdAndId(Integer scannerId, Integer attendanceId);
    public void deleteAttendanceByScannerIdAndId(Integer scannerId, Integer attendanceId);
}
