package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Role;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.contract.RolePayload;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseReadWriteServiceImpl<RolePayload, Role, Long> implements RoleService {
}
