package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Schedule;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.service.EventService;
import edu.miu.cs.cs544.service.ScheduleService;
import edu.miu.cs.cs544.service.SessionService;
import edu.miu.cs.cs544.service.contract.EventPayload;
import edu.miu.cs.cs544.service.contract.SchedulePayLoad;
import edu.miu.cs.cs544.service.contract.SessionPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController extends BaseReadWriteController<SchedulePayLoad, Schedule,Integer> {

    @Autowired
    private ScheduleService sessionService;

    @GetMapping("/{eventId}/sessions")
    public ResponseEntity<List<Session>> getEventSessions(@PathVariable Integer eventId) {
        SchedulePayLoad schedule = sessionService.findById(eventId);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }

        // Access the schedule of the event and retrieve sessions
        List<Session> sessions = schedule.getSessionList();

        return ResponseEntity.ok(sessions);
    }

}
