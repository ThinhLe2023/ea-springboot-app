package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.service.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scanners/{scannerCode}/records")
public class ScannerRecordsController {

    /*private final ScannerService scannerService;

    @Autowired
    public ScannerRecordsController(ScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @GetMapping
    public ResponseEntity<?> getRecordsByScannerCode(@PathVariable String scannerCode) {
        // Assuming you have a method in ScannerService to retrieve records by scanner code
        List<Attendance> records = scannerService.getRecordsByScannerCode(scannerCode);
        return ResponseEntity.ok(records);
    }*/
}
