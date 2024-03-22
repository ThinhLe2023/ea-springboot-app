package edu.miu.cs.cs544.service.mapper;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.service.contract.AccountTypePayload;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class AccountTypeToAccountTypePayloadMapper extends BaseMapper<AccountType, AccountTypePayload> {

	public AccountTypeToAccountTypePayloadMapper(MapperFactory mapperFactory) {
		super(mapperFactory, AccountType.class, AccountTypePayload.class);
	}

}
