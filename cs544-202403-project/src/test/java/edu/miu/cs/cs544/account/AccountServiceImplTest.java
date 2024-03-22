package edu.miu.cs.cs544.account;

import edu.miu.common.exception.ResourceNotFoundException;
import edu.miu.common.service.contract.CommonAuditData;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.repository.AccountRepository;
import edu.miu.cs.cs544.repository.AccountTypeRepository;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.service.AccountServiceImpl;
import edu.miu.cs.cs544.service.contract.*;
import edu.miu.cs.cs544.service.mapper.AccountPayloadToAccountMapper;
import edu.miu.cs.cs544.service.mapper.AccountToAccountPayloadMapper;
import edu.miu.cs.cs544.service.mapper.MemberPayloadToMemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AccountToAccountPayloadMapper accountToAccountPayloadMapper;

    @Mock
    private AccountPayloadToAccountMapper accountPayloadToAccountMapper;

    @Mock
    private MemberPayloadToMemberMapper memberPayloadToMemberMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testCreateAccount() {
        // Prepare test data
        AccountPayload accountPayload = createAccountPayload();
        Account account = createAccountFromPayload(accountPayload);
        Member member = createMemberFromPayload(accountPayload.getMember());

        // Mock behavior
        when(memberRepository.findById(eq(1L))).thenReturn(Optional.of(member));
        when(accountPayloadToAccountMapper.map(eq(accountPayload))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountToAccountPayloadMapper.map(eq(account))).thenReturn(accountPayload);

        // Perform the service call
        AccountPayload createdAccount = accountService.createAccount(accountPayload);

        // Validate the result
        assertEquals(accountPayload.getName(), createdAccount.getName());
        assertEquals(accountPayload.getDescription(), createdAccount.getDescription());
    }

    private Member createMemberFromPayload(MemberPayload memberPayload) {
        Member member = new Member();
        member.setId(memberPayload.getId());
        // Set other properties as needed
        return member;
    }
    @Test
    public void testUpdateAccount() {
        // Prepare test data
        Long accountId = 1L;
        AccountPayload accountPayload = createAccountPayload();
        accountPayload.setId(accountId);

        System.out.println(accountPayload);

        Account account = createAccountFromPayload(accountPayload);
        Member member = createMemberFromPayload(accountPayload.getMember());

        // Mock behavior
        when(accountRepository.findById(eq(accountId))).thenReturn(Optional.of(account));
        when(memberRepository.findById(eq(1L))).thenReturn(Optional.of(member));
        when(accountPayloadToAccountMapper.map(eq(accountPayload))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountToAccountPayloadMapper.map(eq(account))).thenReturn(accountPayload);

        // Perform the service call
        AccountPayload updatedAccount = accountService.updateAccount(accountId, accountPayload);

        // Validate the result
        assertEquals(accountPayload.getName(), updatedAccount.getName());
        assertEquals(accountPayload.getDescription(), updatedAccount.getDescription());
    }



    private AccountPayload createAccountPayload() {
        // Create a sample account payload for testing
        AccountPayload accountPayload = new AccountPayload();
        accountPayload.setName("Test Account");
        accountPayload.setDescription("Test Description");
        accountPayload.setMember(createMemberPayload());

        // Ensure that AccountTypePayload is not null
        AccountTypePayload accountTypePayload = createAccountTypePayload();
        accountPayload.setAccountType(accountTypePayload);

        accountPayload.setAuditData(createCommonAuditData());
        return accountPayload;
    }

    private AccountTypePayload createAccountTypePayload() {
        // Create a sample account type payload for testing
        AccountTypePayload accountTypePayload = new AccountTypePayload();
        accountTypePayload.setId(1L); // Set account type ID
        accountTypePayload.setType("Test Type");
        return accountTypePayload;
    }

    private MemberPayload createMemberPayload() {
        // Create a sample member payload for testing
        MemberPayload memberPayload = new MemberPayload();
        memberPayload.setId(1L); // Set member ID
        return memberPayload;
    }

    private CommonAuditData createCommonAuditData() {
        // Create a sample common audit data for testing
        CommonAuditData auditData = new CommonAuditData();
        auditData.setCreatedBy("Test User");
        auditData.setUpdatedBy("Test User");
        return auditData;
    }

    private Account createAccountFromPayload(AccountPayload accountPayload) {
        // Create an Account entity from the payload for testing
        Account account = new Account();
        account.setId(accountPayload.getId());
        account.setName(accountPayload.getName());
        account.setDescription(accountPayload.getDescription());
        return account;
    }

//    @Test
    public void testGetAttendance_Success() throws ParseException {
        // Prepare test data
        Long accountId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";

        AccountPayload accountPayload = createAccountPayload();
        Account account = new Account();

        when(accountRepository.findById(eq(accountId))).thenReturn(java.util.Optional.of(account));
        when(accountRepository.getAttendanceForAccount(eq(accountId), any(Date.class), any(Date.class)))
                .thenReturn(createMockAttendanceList());

        when(accountToAccountPayloadMapper.map(account)).thenReturn(accountPayload);

        // Perform the service call
        AccountAttendanceSummaryPayload result = accountService.getAttendanceForAccount(accountId, startDate, endDate);

        // Validate the result
        assertNotNull(result);
        assertEquals(accountId, result.getAccountId());
        assertEquals(accountPayload.getName(), result.getAccountName());
        assertEquals(2, result.getTotalSessionRegistered());
        assertEquals(2, result.getTotalAttended());
        assertEquals(0, result.getTotalAbsent());
        assertEquals(2, result.getAttendances().size());
    }

    private List<Object[]> createMockAttendanceList() {
        List<Object[]> attendanceList = new ArrayList<>();
        attendanceList.add(new Object[]{1, 1, 1L, 1L, "2024-01-01 08:00:00"});
        attendanceList.add(new Object[]{2, 2, 2L, 2L, "2024-01-02 08:00:00"});
        return attendanceList;
    }

    @Test
    public void testGetAttendance_AccountNotFound() {
        // Prepare test data
        Long accountId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";

        // Mock repository method to throw ResourceNotFoundException
        when(accountRepository.findById(eq(accountId))).thenReturn(Optional.empty());

        // Perform service call
        try {
            accountService.getAttendanceForAccount(accountId, startDate, endDate);
        } catch (ResourceNotFoundException e) {
            // Validate exception message
            assertEquals("Account not found with ID: " + accountId, e.getMessage());
        }
    }
    @Test
    public void testGetAttendance_InternalServerError() {
        // Prepare test data
        Long accountId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";

        // Mock repository method to throw RuntimeException
        when(accountRepository.findById(eq(accountId))).thenThrow(new RuntimeException("Internal Server Error"));

        // Perform service call
        try {
            accountService.getAttendanceForAccount(accountId, startDate, endDate);
        } catch (RuntimeException e) {
            // Validate exception message
            assertEquals("Internal Server Error", e.getMessage());
        }
    }




}
