package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Scanner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScannerRepository extends BaseRepository<Scanner, Integer>{

    @Query(value = "SELECT a.* FROM attendance a " +
            "JOIN scanner s ON s.id = a.scanner_id " +
            "WHERE s.id = :scannerId", nativeQuery = true)
    List<Attendance> getAttendanceRecordsByScannerId(@Param("scannerId") Integer scannerId);

}
