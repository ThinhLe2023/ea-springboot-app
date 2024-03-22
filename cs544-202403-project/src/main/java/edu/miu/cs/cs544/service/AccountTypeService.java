package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.service.contract.AccountTypePayload;

public interface AccountTypeService extends BaseReadWriteService <AccountTypePayload, AccountType, Long>{

}
