package edu.miu.cs.cs544.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.common.exception.ResourceNotFoundException;
import edu.miu.cs.cs544.controller.AccountController;
import edu.miu.cs.cs544.service.AccountService;
import edu.miu.cs.cs544.service.contract.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testCreateAccount() throws Exception {
        // Prepare test data
        AccountPayload accountPayload = createAccountPayload();

        // Mock service method
        when(accountService.createAccount(eq(accountPayload)))
                .thenReturn(accountPayload);

        // Perform POST request
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountPayload)))
                // Validate response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(accountPayload.getName()))
                .andExpect(jsonPath("$.description").value(accountPayload.getDescription()));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        // Prepare test data
        Long accountId = 1L;
        AccountPayload accountPayload = createAccountPayload();
        accountPayload.setId(accountId);

        // Mock service method
        when(accountService.updateAccount(eq(accountId), ArgumentMatchers.refEq(accountPayload)))
                .thenReturn(accountPayload);

        // Perform PUT request
        mockMvc.perform(put("/accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountPayload)))
                // Validate response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId))
                .andExpect(jsonPath("$.name").value(accountPayload.getName()))
                .andExpect(jsonPath("$.description").value(accountPayload.getDescription()));
    }

    @Test
    public void testGetAttendance_Success() throws Exception {
        // Prepare test data
        Long accountId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";
        AccountAttendanceSummaryPayload attendancePayload = createAttendanceSummaryPayload();

        // Mock service method
        when(accountService.getAttendanceForAccount(eq(accountId), eq(startDate), eq(endDate)))
                .thenReturn(attendancePayload);

        // Perform GET request
        mockMvc.perform(get("/accounts/{id}/attendance/{startDate}/{endDate}", accountId, startDate, endDate))
                // Validate response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(accountId))
                .andExpect(jsonPath("$.accountName").value(attendancePayload.getAccountName()))
                .andExpect(jsonPath("$.totalSessionRegistered").value(attendancePayload.getTotalSessionRegistered()))
                .andExpect(jsonPath("$.totalAbsent").value(attendancePayload.getTotalAbsent()))
                .andExpect(jsonPath("$.totalAttended").value(attendancePayload.getTotalAttended()))
                .andExpect(jsonPath("$.attendances").isArray())
                .andExpect(jsonPath("$.attendances[0].attendanceId").value(attendancePayload.getAttendances().get(0).getAttendanceId()))
                .andExpect(jsonPath("$.attendances[0].session").value(attendancePayload.getAttendances().get(0).getSession()))
        ;
    }

    @Test
    public void testGetAttendance_AccountNotFound() throws Exception {
        // Mock service method to throw ResourceNotFoundException
        when(accountService.getAttendanceForAccount(anyLong(), eq("2024-01-01"), eq("2024-12-31")))
                .thenThrow(new ResourceNotFoundException("Account not found with ID: 1"));

        // Perform GET request
        mockMvc.perform(get("/accounts/1/attendance/2024-01-01/2024-12-31"))
                // Validate response
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAttendance_InternalServerError() throws Exception {
        // Mock service method to throw any other exception
        when(accountService.getAttendanceForAccount(anyLong(), eq("2024-01-01"), eq("2024-12-31")))
                .thenThrow(new RuntimeException("Internal Server Error"));

        // Perform GET request
        mockMvc.perform(get("/accounts/1/attendance/2024-01-01/2024-12-31"))
                // Validate response
                .andExpect(status().isInternalServerError());
    }

    private AccountPayload createAccountPayload() {
        // Create a sample account payload for testing
        AccountPayload accountPayload = new AccountPayload();
        accountPayload.setName("Test Account");
        accountPayload.setDescription("Test Description");
        accountPayload.setMember(createMemberPayload());
        accountPayload.setAccountType(createAccountTypePayload());
        return accountPayload;
    }

    private MemberPayload createMemberPayload() {
        // Create a sample member payload for testing
        MemberPayload memberPayload = new MemberPayload();
        memberPayload.setId(1L); // Set member ID
        return memberPayload;
    }

    private AccountTypePayload createAccountTypePayload() {
        // Create a sample account type payload for testing
        AccountTypePayload accountTypePayload = new AccountTypePayload();
        accountTypePayload.setId(1L); // Set account type ID
        accountTypePayload.setType("Test Type");
        return accountTypePayload;
    }

    private AccountAttendanceSummaryPayload createAttendanceSummaryPayload() {
        // Create a sample attendance payload for testing
        AccountAttendanceSummaryPayload attendancePayload = new AccountAttendanceSummaryPayload();
        attendancePayload.setAccountId(1L);
        attendancePayload.setAccountName("Test Account");
        attendancePayload.setTotalSessionRegistered(10);
        attendancePayload.setTotalAbsent(2);
        attendancePayload.setTotalAttended(8);
        attendancePayload.setAttendances(List.of(createAttendancePayload()));
        return attendancePayload;
    }

    private AttendancePayload createAttendancePayload() {
        // Create a sample attendance payload for testing
        AttendancePayload attendancePayload = new AttendancePayload();
//        attendancePayload.setAttendanceId(1);
//        attendancePayload.setSession(1L);
//        attendancePayload.setMemberId(1L);
//        attendancePayload.setScannerId(1L);
//        attendancePayload.setAttendanceDateTIme("2024-01-01 08:00:00");
        return attendancePayload;
    }
}
