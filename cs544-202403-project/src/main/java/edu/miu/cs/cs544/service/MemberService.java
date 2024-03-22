package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.contract.MemberPayload;

import java.util.List;
public interface MemberService extends BaseReadWriteService <MemberPayload, Member, Long>{
    void assignRoleToMember(Long member_id, List<Long> role_ids);
    MemberPayload findMemberWithRoles(Long memberId);

    void deleteRolesForMember(Long memberId);

    void deleteRoleForMember( Long member_id, Long role_id);

    void updateOrInsertRole(Long member_id,List<Long> role_ids);

    Long calculateAttendanceForPerson(Long memberId, Integer eventId);

    void saveAll(List<Member> m);

    List<Member> findMembersByAccountBalanceLessThanFive();
}
