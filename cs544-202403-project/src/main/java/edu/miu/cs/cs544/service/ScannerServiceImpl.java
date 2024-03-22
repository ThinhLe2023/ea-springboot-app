package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;

import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.repository.AttendanceRepository;
import edu.miu.cs.cs544.repository.ScannerRepository;
import edu.miu.cs.cs544.service.contract.ScannerPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScannerServiceImpl extends BaseReadWriteServiceImpl<ScannerPayload, Scanner, Integer> implements ScannerService {
    @Autowired
    ScannerRepository scannerRepository;
    @Override
    public Scanner getScannerById(Integer scannerId) {
        return scannerRepository.findById(scannerId).orElse(null);
    }

    public List<Attendance> getAttendanceRecordsByScannerId(Integer scannerId) {
        return scannerRepository.getAttendanceRecordsByScannerId(scannerId);
    }


    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getRecordsByScannerCode(String scannerCode) {
        return attendanceRepository.findByScannerCode(scannerCode);
    }
}
