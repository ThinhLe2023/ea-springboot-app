package edu.miu.cs.cs544.service;

import edu.miu.common.repository.BaseRepository;
import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.repository.EventRepository;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.repository.SessionRepository;
import edu.miu.cs.cs544.service.contract.EventPayload;
import org.springframework.beans.factory.annotation.Autowired;
import edu.miu.cs.cs544.service.contract.SessionPayload;
import edu.miu.cs.cs544.service.mapper.SessionToSessionPayloadMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends BaseReadWriteServiceImpl<EventPayload,Event,Integer> implements EventService {
    @Autowired
    EventRepository eventRepository;
    @Override
    public Long getTotalEventAttendanace(Long eventId) {
        return eventRepository.getTotalEventAttendanace(eventId);
    }

    @Autowired
    protected BaseRepository<Event, Integer> baseRepository;

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionToSessionPayloadMapper mapper;
    @Autowired
    private SessionService sessionService;

    @Transactional
    @Override
    public List<SessionPayload> getSessionsByEventId(Integer eventId) {
        List<Session> sessions = eventRepository.findAllSessionsByEventId(eventId);
        return sessions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addSessionToEvent(Session session, Integer eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            session.setEvent(event);
            sessionService.saveSession(session);
        } else {
            throw new EntityNotFoundException("Event not found with id: " + eventId);
        }
    }


    @Transactional
    @Override
    public SessionPayload getSessionById(Integer sessionId) {
        Optional<Session> session = eventRepository.getSessionById(sessionId);
        return session.map(value -> mapper.map(value)).orElse(null);
    }

    @Transactional
    @Override
    public void deleteSessionById(Integer eventId, Integer sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            if (!session.getEvent().getEventId().equals(eventId)) {
                throw new IllegalArgumentException("Session does not belong to Event with id: " + eventId);
            }
            sessionRepository.delete(session); // Delete the session
        } else {
            throw new EntityNotFoundException("Session not found with id: " + sessionId);
        }
    }




    public Session updateSessionById(Integer sessionId, Session updatedSession) throws EntityNotFoundException {
        Optional<Session> existingSessionOptional = sessionRepository.findById(sessionId);
        if (existingSessionOptional.isPresent()) {
            Session existingSession = existingSessionOptional.get();
            existingSession.setDate(updatedSession.getDate());
            existingSession.setStartTime(updatedSession.getStartTime());
            existingSession.setEndTime(updatedSession.getEndTime());
            return sessionRepository.save(existingSession); // Persist changes
        } else {
            throw new EntityNotFoundException("Session not found with id: " + sessionId);
        }
    }
}
