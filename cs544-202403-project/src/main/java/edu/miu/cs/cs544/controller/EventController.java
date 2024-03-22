package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.service.EventService;
import edu.miu.cs.cs544.service.SessionService;
import edu.miu.cs.cs544.service.contract.EventPayload;
import edu.miu.cs.cs544.service.contract.SessionPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController extends BaseReadWriteController<EventPayload,Event,Integer> {


    @Autowired
    private EventService eventService;
    @Autowired
    SessionService sessionService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("{eventId}/attendance")
    public  Long getTotalEventAttendanace(@PathVariable("eventId") Long eventId){

        return eventService.getTotalEventAttendanace(eventId);
    }

    @GetMapping("/{eventId}/sessions")
    public ResponseEntity<List<SessionPayload>> getSessionsByEventId(@PathVariable Integer eventId) {
        List<SessionPayload> sessions = eventService.getSessionsByEventId(eventId);
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{eventId}/sessions/{sessionId}")
    public ResponseEntity<SessionPayload> getSessionById(@PathVariable Integer eventId, @PathVariable Integer sessionId) {
        SessionPayload sessionPayload = eventService.getSessionById(sessionId);
        if (sessionPayload == null) {
            return ResponseEntity.notFound().build();  // Return 404 if session not found
        }
        return ResponseEntity.ok(sessionPayload);  // Return 200 with session payload
    }



    @PostMapping("/{eventId}/sessions")
    public ResponseEntity<Session> addSessionToEvent(@PathVariable Integer eventId, @RequestBody Session session) {
        eventService.addSessionToEvent(session, eventId);
        return ResponseEntity.ok(session);
    }


    @DeleteMapping("/{eventId}/sessions/{sessionId}")
    public ResponseEntity<String> deleteSessionFromEvent(@PathVariable Integer eventId, @PathVariable Integer sessionId) {
        eventService.deleteSessionById(eventId, sessionId);
        return ResponseEntity.ok("Session successfully deleted from the event.");
    }


    @PutMapping("/{eventId}/sessions/{sessionId}")
    public ResponseEntity<Session> updateSession(@PathVariable Integer eventId, @PathVariable Integer sessionId, @RequestBody Session updatedSession)  {
        Session updated = eventService.updateSessionById(sessionId, updatedSession);
        return ResponseEntity.ok(updated);
    }


}