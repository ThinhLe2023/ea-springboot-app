package edu.miu.cs.cs544.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.service.EventService;
import edu.miu.cs.cs544.service.SessionService;
import edu.miu.cs.cs544.service.contract.EventPayload;
import edu.miu.cs.cs544.service.contract.SessionPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Mock
    private SessionService sessionService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
        eventController = new EventController(eventService);
    }

    @Test
    void testGetSessionsByEventId() {
        int eventId = 1;
        List<SessionPayload> sessions = Arrays.asList(
                new SessionPayload(),
                new SessionPayload()
        );
        sessions.get(0).setSessionId(1);
        sessions.get(0).setDate(LocalDate.now());
        sessions.get(0).setStartTime(LocalTime.of(9, 0));
        sessions.get(0).setEndTime(LocalTime.of(12, 0));

        sessions.get(1).setSessionId(2);
        sessions.get(1).setDate(LocalDate.now());
        sessions.get(1).setStartTime(LocalTime.of(13, 0));
        sessions.get(1).setEndTime(LocalTime.of(16, 0));

        when(eventService.getSessionsByEventId(eventId)).thenReturn(sessions);

        ResponseEntity<List<SessionPayload>> response = eventController.getSessionsByEventId(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessions, response.getBody());
    }

    @Test
    void testGetSessionById_SessionExists() {
        int eventId = 1;
        int sessionId = 1;

        SessionPayload session = new SessionPayload();
        session.setSessionId(1);
        session.setDate(LocalDate.now());
        session.setStartTime(LocalTime.of(9, 0));
        session.setEndTime(LocalTime.of(12, 0));

        when(eventService.getSessionById(sessionId)).thenReturn(session);

        ResponseEntity<SessionPayload> response = eventController.getSessionById(eventId, sessionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(session, response.getBody());
    }

    @Test
    void testGetSessionById_SessionNotExists() {
        int eventId = 1;
        int sessionId = 1;
        when(eventService.getSessionById(sessionId)).thenReturn(null);

        ResponseEntity<SessionPayload> response = eventController.getSessionById(eventId, sessionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddSessionToEvent() {
        int eventId = 1;
        Session session = new Session();
        session.setSessionId(1);
        session.setDate(LocalDate.now());
        session.setStartTime(LocalTime.of(9, 0));
        session.setEndTime(LocalTime.of(12, 0));

        ResponseEntity<Session> response = eventController.addSessionToEvent(eventId, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService, times(1)).addSessionToEvent(session, eventId);
    }

    @Test
    void testDeleteSessionFromEvent() {
        int eventId = 1;
        int sessionId = 1;

        ResponseEntity<String> response = eventController.deleteSessionFromEvent(eventId, sessionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService, times(1)).deleteSessionById(eventId, sessionId);
    }

    @Test
    void testUpdateSession() {
        int eventId = 1;
        int sessionId = 1;
        Session updatedSession = new Session();

        ResponseEntity<Session> response = eventController.updateSession(eventId, sessionId, updatedSession);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService, times(1)).updateSessionById(sessionId, updatedSession);
    }
}

