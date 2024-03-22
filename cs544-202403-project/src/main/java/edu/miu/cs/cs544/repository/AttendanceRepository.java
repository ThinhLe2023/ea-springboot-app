package edu.miu.cs.cs544.repository;


import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends BaseRepository<Attendance, Integer> {

   List<Attendance> findByScannerCode(String scannerCode);
   Optional<Attendance> findByAttendanceIdAndScannerId(Integer attendanceId, Integer scannerId);
   boolean existsByScanner_IdAndAttendanceId(Integer scannerId, Integer attendanceId);
   void deleteByScanner_IdAndAttendanceId(Integer scannerId, Integer attendanceId);
   @Query(value = "SELECT a FROM Attendance a JOIN a.scanner s WHERE s.id = :scannerId")
   List<Attendance> getAttendanceRecordsByScannerId(@Param("scannerId") Integer scannerId);

   @Query("SELECT a FROM Attendance a WHERE a.scanner.id = :scannerId")
    List<Attendance> findAttendancesByScannerId(@Param("scannerId") Integer scannerId);

    @Query("select a from Attendance a where a.session.sessionId = :sessionId and a.member.id = :memberId ")
    Collection<? extends Attendance> findBySessionIsAndMemberId(Integer sessionId, Long memberId);
}
