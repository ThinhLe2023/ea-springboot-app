package edu.miu.cs.cs544.controller;


import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.service.SessionService;
import edu.miu.cs.cs544.service.contract.SessionPayload;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sessions")
public class SessionController extends BaseReadWriteController<SessionPayload, Session, Integer> {

    @Autowired
    SessionService sessionService;

    @GetMapping("/greater/{id}")
    public ResponseEntity<?> findAfter(@PathVariable Long id) {
        List<SessionPayload> allSessions = sessionService.findAll();
        List<SessionPayload> sessionsAfter = allSessions.stream()
                .filter(session -> session.getSessionId() > id)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessionsAfter);
    }


}

