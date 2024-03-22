package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.AccountType;

public interface AccountTypeRepository extends BaseRepository<AccountType, Long>{

    AccountType findByType(String type);
}
