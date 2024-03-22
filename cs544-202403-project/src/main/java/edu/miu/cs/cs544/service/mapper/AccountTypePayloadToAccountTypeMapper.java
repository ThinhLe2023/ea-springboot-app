package edu.miu.cs.cs544.service.mapper;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.service.contract.AccountTypePayload;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class AccountTypePayloadToAccountTypeMapper extends BaseMapper<AccountTypePayload, AccountType>{

	public AccountTypePayloadToAccountTypeMapper(MapperFactory mapperFactory) {
		super(mapperFactory, AccountTypePayload.class, AccountType.class);
	}
}
