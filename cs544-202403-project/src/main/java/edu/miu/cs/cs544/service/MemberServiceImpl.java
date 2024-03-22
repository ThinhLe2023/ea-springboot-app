package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Role;
import edu.miu.cs.cs544.repository.EventRepository;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl extends BaseReadWriteServiceImpl<MemberPayload, Member, Long> implements MemberService {

    public MemberServiceImpl() {
    }

    public MemberServiceImpl(MemberRepository memberRepository, EventRepository eventRepository,
                             BaseMapper<Member, MemberPayload> requestMapper) {
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
        this.requestMapper = requestMapper;
    }

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private EventRepository eventRepository;


    public void assignRoleToMember(Long member_id, List<Long> role_ids) {
        for (Long role_id : role_ids) {
            memberRepository.assignRoleToMember(member_id, role_id);
        }
    }

    @Autowired
    private BaseMapper<Member, MemberPayload> requestMapper;

    public MemberPayload findMemberWithRoles(Long memberId) {
        return requestMapper.map(memberRepository.findMemberWithRoles(memberId));
    }

    public void deleteRolesForMember(Long memberId) {
        memberRepository.deleteRolesForMember(memberId);
    }


    public void deleteRoleForMember(Long member_id, Long role_id) {
        memberRepository.deleteRoleForMember(member_id, role_id);
    }

    public void updateOrInsertRole(Long member_id, List<Long> role_ids) {
        List<Long> roles_found = memberRepository.findMemberWithRoles(member_id)
                .getRoles().stream().map(Role::getId)
                .toList();

        List<Long> needToBeAdded = role_ids.stream()
                .filter(item -> !roles_found.contains(item))
                .collect(Collectors.toList());

        if (needToBeAdded.size() > 0) {
            assignRoleToMember(member_id, needToBeAdded);
        }

        List<Long> needToBeDeleted = roles_found.stream()
                .filter(item -> !role_ids.contains(item))
                .collect(Collectors.toList());

        for (Long role_id : needToBeDeleted) {
            deleteRoleForMember(member_id, role_id);
        }


    }

    public Long calculateAttendanceForPerson(Long memberId, Integer eventId) {
        return eventRepository.getTotalAttendanceByEventAndMember(eventId, memberId);
    }


    public List<Member> findMembersByAccountBalanceLessThanFive() {
        return memberRepository.findMembersByAccountBalanceLessThanFive();
    }


    public void saveAll(List<Member> m) {
        memberRepository.saveAll(m);
    }
}
