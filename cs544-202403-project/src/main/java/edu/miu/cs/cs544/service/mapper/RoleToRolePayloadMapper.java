package edu.miu.cs.cs544.service.mapper;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Role;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.contract.RolePayload;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;


@Component
public class RoleToRolePayloadMapper  extends BaseMapper<Role, RolePayload> {
    public RoleToRolePayloadMapper(MapperFactory mapperFactory) {
        super(mapperFactory, Role.class, RolePayload.class);
    }
}
