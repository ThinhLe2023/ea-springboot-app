package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.common.exception.ResourceNotFoundException;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.service.AccountService;
import edu.miu.cs.cs544.service.contract.AccountPayload;
import edu.miu.cs.cs544.service.contract.AccountAttendanceSummaryPayload;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController extends BaseReadWriteController<AccountPayload, Account, Long> {
    @Autowired
    private AccountService accountService;
    @Override
    public ResponseEntity<AccountPayload> create(@RequestBody AccountPayload accountPayload) {
        try {
            // Create the account
            AccountPayload createdAccount = accountService.createAccount(accountPayload);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (ResourceNotFoundException e) {
            // if member not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            // other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<AccountPayload> update(@PathVariable Long id, @RequestBody AccountPayload accountPayload) {
        try {
            // Update the account
            AccountPayload updatedAccount = accountService.updateAccount(id, accountPayload);
            return ResponseEntity.ok(updatedAccount);
        } catch (ResourceNotFoundException e) {
            // if account or member not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/attendance/{startDate}/{endDate}")
    public ResponseEntity<AccountAttendanceSummaryPayload> getAttendance(@PathVariable Long id, @PathVariable String startDate, @PathVariable String endDate) {
        try {
            // Get the attendance
            AccountAttendanceSummaryPayload attendancePayload = accountService.getAttendanceForAccount(id, startDate, endDate);
            return ResponseEntity.ok(attendancePayload);
        } catch (ResourceNotFoundException e) {
            // if account not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
