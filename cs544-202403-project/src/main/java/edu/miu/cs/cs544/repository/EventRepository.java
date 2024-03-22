package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import edu.miu.cs.cs544.domain.Session;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends BaseRepository<Event,Integer> {
    @Query(value="SELECT COUNT(att.attendance_id) AS attendancecount " +
            "FROM event e " +
            "JOIN session AS ss ON ss.e_id = e.event_id " +
            "JOIN attendance AS att on att.sid = ss.session_id " +
            "WHERE e.event_id =:eventId" , nativeQuery = true)
    Long getTotalEventAttendanace(@Param("eventId")Long  eventId);


    @Query(value="SELECT COUNT(att.attendance_id) AS attendancecount " +
            "FROM event e " +
            "JOIN session AS ss ON ss.e_id = e.event_id " +
            "JOIN attendance AS att on att.sid = ss.session_id " +
            "WHERE e.event_id =:eventId and att.member_id = :memberId " , nativeQuery = true)
    Long getTotalAttendanceByEventAndMember(@Param("eventId")Integer  eventId, @Param("memberId")Long  memberId);


    @Modifying
    @Transactional
    @Query("SELECT s FROM Session s WHERE s.event.eventId = :eventId")
    List<Session> findAllSessionsByEventId(@Param("eventId") Integer eventId);

    @Query("SELECT s FROM Session s WHERE s.sessionId = :sessionId")
    Optional<Session> getSessionById(@Param("sessionId") Integer sessionId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Session s WHERE s.sessionId = :sessionId AND s.event.eventId = :eventId ")
    void deleteSessionById(@Param("sessionId") Integer sessionId, @Param("eventId") Integer eventId);

}
