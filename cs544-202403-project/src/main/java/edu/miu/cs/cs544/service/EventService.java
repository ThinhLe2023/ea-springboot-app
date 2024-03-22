package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.service.contract.EventPayload;
import edu.miu.cs.cs544.service.contract.SessionPayload;

import java.util.List;

public interface EventService extends BaseReadWriteService<EventPayload,Event,Integer> {
    List<SessionPayload> getSessionsByEventId(Integer eventId);

    void addSessionToEvent(Session session, Integer eventId);

    SessionPayload getSessionById(Integer sessionId);
    void deleteSessionById(Integer sessionId, Integer id);

    Session updateSessionById(Integer sessionId, Session updatedSession);

    Long getTotalEventAttendanace(Long eventId);
}
