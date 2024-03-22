package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.service.contract.AccountPayload;
import edu.miu.cs.cs544.service.contract.AccountAttendanceSummaryPayload;

import java.util.List;

public interface AccountService extends BaseReadWriteService <AccountPayload, Account, Long>{
    public AccountPayload createAccount(AccountPayload accountPayload);

    AccountPayload updateAccount(Long accountId, AccountPayload accountPayload);

    void saveAll(List<Account> accounts);

    public AccountAttendanceSummaryPayload getAttendanceForAccount(Long accountId, String startDate, String endDate);
}
