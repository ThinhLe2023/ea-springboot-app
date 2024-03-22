package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.domain.LocationType;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.*;
import edu.miu.cs.cs544.service.contract.*;
import edu.miu.cs.cs544.service.mapper.SessionPayloadToSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/init")
public class InitialController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ScannerService scannerService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private SessionPayloadToSessionMapper sessionMapper;

    @GetMapping("/scanner")
    public ResponseEntity<String> initialDataForScanner(){
        initialData();
        return ResponseEntity.ok("Success");
    }

    private void initialData() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Member m = new Member("firstName" + i, "lastName" + i,
                    "email@"+ i +"gmail.com", "barcode" + i);
            members.add(m);
        }
        memberService.saveAll(members);

        AccountType accountType = new AccountType("Student", "For student");
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Account m = new Account("Account", "Initial Account",accountType);
            accounts.add(m);
        }
        accountService.saveAll(accounts);

        //Location and Scanner
        LocationPayload location = new LocationPayload();
        location.setName("Agiro");
        location.setDescription("lunch or dinner");
        location.setType(LocationType.DINING);

        ScannerPayload scanner = new ScannerPayload();
        scanner.setName("Agiro scanner");
        scanner.setLocation(location);
        scannerService.create(scanner);

        //Given
        EventPayload event = new EventPayload();
        event.setName("Event name");
        event.setDescription("Event description");
        event = eventService.create(event);

        SessionPayload session = new SessionPayload();
        session.setDate(LocalDate.now());
        session.setStartTime(LocalTime.now().minusHours(2));
        session.setEndTime(LocalTime.now().minusHours(1));

        SessionPayload session1 = new SessionPayload();
        session1.setDate(LocalDate.now());
        session1.setStartTime(LocalTime.now().minusHours(1));
        session1.setEndTime(LocalTime.now());

        eventService.addSessionToEvent(sessionMapper.map(session), event.getEventId());
        eventService.addSessionToEvent(sessionMapper.map(session1), event.getEventId());


        AttendancePayload attendance = new AttendancePayload();
        attendance.setMember(memberService.findById(1L));
        attendance.setSession(session);
        attendance.setScanner(scannerService.findById(1));
        attendance = attendanceService.create(attendance);

        AttendancePayload attendance1 = new AttendancePayload();
        attendance1.setMember(memberService.findById(2L));
        attendance1.setSession(session1);
        attendance1.setScanner(scannerService.findById(1));
        attendance1 = attendanceService.create(attendance1);
    }
}
