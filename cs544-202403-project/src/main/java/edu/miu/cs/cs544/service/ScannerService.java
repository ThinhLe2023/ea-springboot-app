package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.contract.ScannerPayload;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScannerService extends BaseReadWriteService <ScannerPayload, Scanner, Integer>{

   // List<Attendance> getRecordsByScannerCode(String scannerCode);
   Scanner getScannerById(Integer scannerId);

    //List<Attendance> getAttendanceRecordsByScannerId(Integer scannerId);
}
