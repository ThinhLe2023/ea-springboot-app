package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Disabled
public class EventRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    EventRepository repository;
    @Test
    public void countAttendanceByEventAndMember_success() {
        //Given
        Event event = new Event("event name", "description");
        Session session = new Session();
        session.setStartTime(LocalTime.now().minusHours(1));
        session.setEndTime(LocalTime.now());
        session.setEvent(event);
        event.setSessions(Arrays.asList(session));
        event = em.persist(event);

        Attendance attendance = new Attendance();
        attendance.setPresent(true);
        attendance.setSession(session);
        attendance = em.persist(attendance);

        Member member = new Member("firstName", "lastName", "emailAddress", "barcode");
        member.setAttendances(Arrays.asList(attendance));
        member = em.persist(member);
        em.flush();

        //when
        Long rs = repository.getTotalAttendanceByEventAndMember(event.getEventId(), member.getId());

        //then
        assertEquals(1, rs);
    }

    @Test
    public void countAttendanceByEventId_success() {
        //Given
        Event event = new Event("event name", "description");
        Session session = new Session();
        session.setStartTime(LocalTime.now().minusHours(2));
        session.setEndTime(LocalTime.now().minusHours(1));

        Session session1 = new Session();
        session1.setStartTime(LocalTime.now().minusHours(1));
        session1.setEndTime(LocalTime.now());
        event = em.persist(event);
        session.setEvent(event);
        session1.setEvent(event);
        session = em.persist(session);
        session1 = em.persist(session1);

        Attendance attendance = new Attendance();
        attendance.setPresent(true);
        attendance.setSession(session);
        attendance = em.persist(attendance);

        Attendance attendance1 = new Attendance();
        attendance1.setPresent(true);
        attendance1.setSession(session1);
        attendance1 = em.persist(attendance1);

        Member member = new Member("firstName", "lastName", "emailAddress", "barcode");
        member.setAttendances(Arrays.asList(attendance, attendance1));
        member = em.persist(member);
        em.flush();

        //when
        Long rs = repository.getTotalEventAttendanace(event.getEventId().longValue());

        //then
        assertEquals(2, rs);
    }

}