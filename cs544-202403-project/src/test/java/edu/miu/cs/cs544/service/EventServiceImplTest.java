//package edu.miu.cs.cs544.service;
//
//import edu.miu.cs.cs544.domain.Event;
//import edu.miu.cs.cs544.domain.Session;
//import edu.miu.cs.cs544.repository.EventRepository;
//import edu.miu.cs.cs544.service.contract.SessionPayload;
//import edu.miu.cs.cs544.service.mapper.SessionToSessionPayloadMapper;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class EventServiceImplTest {
//
//
//    @TestConfiguration
//    static class EventServiceImpTestConfig{
//        @Bean
//        public EventService eventService(){
//            return new EventServiceImpl();
//        }
//    }
//
//    @Autowired
//    private EventService eventService;
//
//    @MockBean
//    private EventRepository eventRepository;
//
//    @Mock
//    private SessionToSessionPayloadMapper mapper; // Mock mapper for testing
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this); // Initialize mocks
//    }
//
//    @Test
//    public void testGetSessionsByEventId_WithExistingEvent() throws Exception {
//        // Given (mock data)
//        Integer eventId = 1;
//        List<Session> expectedSessions = Arrays.asList(new Session(), new Session());
//        Event event = new Event();
//        event.setEventId(eventId);
//
//        Mockito.when(eventRepository.findAllSessionsByEventId(eventId)).thenReturn(expectedSessions);
//        Mockito.when(mapper.map(expectedSessions.get(0))).thenReturn(new SessionPayload()); // Mock mapping for first session
//        Mockito.when(mapper.map(expectedSessions.get(1))).thenReturn(new SessionPayload()); // Mock mapping for second session
//
//        // When
//        List<SessionPayload> actualSessions = eventService.getSessionsByEventId(eventId);
//
//        // Then (assertions)
//        assertEquals(expectedSessions.size(), actualSessions.size());
//        for (int i = 0; i < expectedSessions.size(); i++) {
//            verify(mapper).map(expectedSessions.get(i)); // Verify mapper was called for each session
//        }
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testGetSessionsByEventId_WithNonExistingEvent() throws Exception {
//        // Given (event not found)
//        Integer eventId = 1;
//        Mockito.when(eventRepository.findAllSessionsByEventId(eventId)).thenReturn(Collections.emptyList());
//
//        // When
//        eventService.getSessionsByEventId(eventId);
//
//        // Then (exception expected)
//    }
//}
