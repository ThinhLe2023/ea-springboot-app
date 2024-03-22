package edu.miu.cs.cs544.service;

import edu.miu.common.domain.AuditData;
import edu.miu.common.exception.ResourceNotFoundException;
import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.repository.AccountRepository;
import edu.miu.cs.cs544.repository.AccountTypeRepository;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.repository.SessionRepository;
import edu.miu.cs.cs544.service.contract.AccountPayload;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import edu.miu.cs.cs544.service.contract.AccountAttendanceSummaryPayload;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.mapper.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class AccountServiceImpl extends BaseReadWriteServiceImpl<AccountPayload, Account, Long> implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountPayloadToAccountMapper accountPayloadToAccountMapper;

    @Autowired
    private AccountToAccountPayloadMapper accountToAccountPayloadMapper;

    @Autowired
    private MemberPayloadToMemberMapper memberPayloadToMemberMapper;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ScannerService scannerService;


    @Override
    public AccountPayload createAccount(AccountPayload accountPayload) {

        // Check if the account type exists
        AccountType accountType = accountTypeRepository.findByType(accountPayload.getAccountType().getType());
        if (accountType == null) {
            // If account type doesn't exist, create a new one
            accountType = new AccountType();
            accountType.setType(accountPayload.getAccountType().getType());
            accountType.setDescription("Newly created account type for " + accountPayload.getAccountType().getType());
            accountType = accountTypeRepository.save(accountType);
        }

        // Convert the AccountPayload to Account entity
        Account account = accountPayloadToAccountMapper.map(accountPayload);

        // Set the account type
        account.setAccountType(accountType);
        // Set the member if it exists
        if (accountPayload.getMember() != null) {
            // Extract member from accountPayload
            Long memberId = accountPayload.getMember().getId();
            if (memberId != null) {
                // Get the member from the database
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + memberId));
                account.setMember(member);
            } else if (accountPayload.getMember().getFirstName() != null ||
                    accountPayload.getMember().getLastName() != null ||
                    accountPayload.getMember().getEmailAddress() != null) {
                // Create a new member
                MemberPayload member = accountPayload.getMember();
                Member m = memberPayloadToMemberMapper.map(member);
                m = memberRepository.save(m);
                account.setMember(m);
            }
        }
        // Set the AuditData for creation
        AuditData auditData = new AuditData();
        auditData.setCreatedBy(accountPayload.getAuditData().getCreatedBy());
        auditData.setCreatedOn(LocalDateTime.now());
        auditData.setCreatedByAppUser("Web Service");
        account.setAuditData(auditData);
        // Save the account
        account = accountRepository.save(account);
        // Convert the saved account entity back to a payload and return it
        return accountToAccountPayloadMapper.map(account);
    }

    @Override
    public AccountPayload updateAccount(Long accountId, AccountPayload accountPayload) {
        // Find the account by ID
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        // Check if the account type exists
        AccountType accountType = accountTypeRepository.findByType(accountPayload.getAccountType().getType());
        if (accountType == null) {
            // If account type doesn't exist, create a new one
            accountType = new AccountType();
            accountType.setType(accountPayload.getAccountType().getType());
            accountType.setDescription("Newly created account type for " + accountPayload.getAccountType().getType());
            accountType = accountTypeRepository.save(accountType);
        }

        // Update the existing account with the new data
        existingAccount.setName(accountPayload.getName());
        existingAccount.setDescription(accountPayload.getDescription());
        existingAccount.setAccountType(accountType);

        // Set the member if it exists
        if (accountPayload.getMember() != null) {
            // Extract member from accountPayload
            Long memberId = accountPayload.getMember().getId();
            if (memberId != null) {
                // Get the member from the database
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + memberId));
                existingAccount.setMember(member);
            } else if (accountPayload.getMember().getFirstName() != null ||
                    accountPayload.getMember().getLastName() != null ||
                    accountPayload.getMember().getEmailAddress() != null) {
                // Create a new member
                MemberPayload member = accountPayload.getMember();
                Member m = memberPayloadToMemberMapper.map(member);
                m = memberRepository.save(m);
                existingAccount.setMember(m);
            }
        }

        // Set the AuditData for update
        AuditData auditData = existingAccount.getAuditData();
        if (auditData == null) {
            auditData = new AuditData();
        }
        auditData.setUpdatedBy(accountPayload.getAuditData().getUpdatedBy());
        auditData.setUpdatedOn(LocalDateTime.now());
        auditData.setUpdatedByAppUser("Web Service");

        // Save the updated account
        existingAccount = accountRepository.save(existingAccount);

        // Convert the saved account entity back to a payload and return it
        return accountToAccountPayloadMapper.map(existingAccount);
    }




    @Override
    public void saveAll(List<Account> accounts) {
        accountRepository.saveAll(accounts);
    }

    public AccountAttendanceSummaryPayload getAttendanceForAccount(Long accountId, String startDate, String endDate) {
        AccountPayload accountPayload = accountRepository.findById(accountId)
                .map(accountToAccountPayloadMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));
        Date convertedStartDate = null;
        Date convertedEndtDate = null;
        try {
            // Example usage
            convertedStartDate = convertToDate(startDate);
            convertedEndtDate = convertToDate(endDate);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        System.out.println("Calling getAttendanceForAccount with accountId: " + accountId + ", startDate: " + startDate + ", endDate: " + endDate + " ...");
        List<Object[]> attendanceList= accountRepository.getAttendanceForAccount(accountId, convertedStartDate, convertedEndtDate);

        System.out.println("Attendance List: " + attendanceList.toString());
        // Initialize a list to store mapped AttendancePayload objects
        List<AttendancePayload> attendancePayloadList = new ArrayList<>();
        AccountAttendanceSummaryPayload accountAttendanceSummaryPayload = new AccountAttendanceSummaryPayload();
        try{
            // Iterate over each Attendance entity and map it to AttendancePayload
            for (Object[] attendance : attendanceList) {
                for (Object o : attendance) {
                    System.out.println("Attendance: " + o);
                }
                AttendancePayload attendancePayload = new AttendancePayload();

//                attendancePayload.setAttendanceId((attendance[0] != null) ? ((Integer) attendance[0]).longValue() : null);
                attendancePayload.setSession((attendance[1] != null) ? sessionService.findById(((Integer) attendance[1]).intValue()) : null);
//                attendancePayload.setMemberId((attendance[2] != null) ? memberService.findById(((Long) attendance[2]).longValue()) : null);
//                attendancePayload.setScannerId(attendance[3] != null ? scannerService.findById(((Integer) attendance[3]).intValue()) : null);
//                attendancePayload.setAttendanceDateTIme(LocalDateTime.parse(attendance[4].toString()));
                attendancePayloadList.add(attendancePayload);
            }
            System.out.println("Attendance Payload List: " + attendancePayloadList.toString());
            accountAttendanceSummaryPayload.setAccountId(accountPayload.getId());
            accountAttendanceSummaryPayload.setAccountName(accountPayload.getName());
            accountAttendanceSummaryPayload.setAccountType(accountPayload.getAccountType().getType());
            accountAttendanceSummaryPayload.setMemberId(accountPayload.getMember().getId());
            accountAttendanceSummaryPayload.setAccountBalance(accountPayload.getBalance());
            int totalSession = accountRepository.countTotalSessionsForAccount(accountId, convertedStartDate, convertedEndtDate);
            System.out.println("Total Session: " + totalSession);
            accountAttendanceSummaryPayload.setAttendances(attendancePayloadList);
            accountAttendanceSummaryPayload.setTotalSessionRegistered(totalSession);
            accountAttendanceSummaryPayload.setTotalAttended(attendancePayloadList.size());
            accountAttendanceSummaryPayload.setTotalAbsent(totalSession - attendancePayloadList.size());
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Error parsing date: " + e.getMessage());
        }
        return accountAttendanceSummaryPayload;
    }


    public static Date convertToDate(String dateString) throws ParseException {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Parse the string to a java.util.Date object
        Date date = dateFormat.parse(dateString);

        return date;
    }
}
