package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.service.contract.AccountTypePayload;
import org.springframework.stereotype.Service;

@Service

public class AccountTypeServiceImpl extends BaseReadWriteServiceImpl<AccountTypePayload, AccountType, Long> implements AccountTypeService {

}
