package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.Attendance;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AccountRepository extends BaseRepository<Account, Long>{

    @Query(value = "SELECT a.attendance_id, a.sid, a.member_id, a.scanner_id, a.attendance_datetime FROM Attendance a " +
            "JOIN Account ac ON ac.member_id = a.member_id " +
            "JOIN Scanner s ON s.id = a.scanner_id AND s.account_type_id = ac.account_type_id " +
            "WHERE ac.id = :accountId AND DATE(a.attendance_datetime) BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Object[]> getAttendanceForAccount(Long accountId, Date startDate, Date endDate);

    @Query(value = "SELECT COUNT(s.session_id) FROM Session  s " +
            "JOIN Schedule sc ON sc.schedule_id = s.schedule_id " +
            "JOIN Event e ON e.event_id = sc.event_id " +
//            "JOIN member_event me ON me.event_id = e.event_id " +
            "JOIN Account ac ON ac.member_id = 1 " +
            "WHERE ac.id = :accountId " +
            "AND DATE(s.date) BETWEEN :startDate AND :endDate",
            nativeQuery = true
    )
    int countTotalSessionsForAccount(Long accountId, Date startDate, Date endDate);


}
