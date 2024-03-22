package edu.miu.cs.cs544.member;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Role;
import edu.miu.cs.cs544.repository.EventRepository;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.service.MemberServiceImpl;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {


    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private BaseMapper<Member, MemberPayload> requestMapper;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    public void setUp() {
        memberService = new MemberServiceImpl(memberRepository, eventRepository, requestMapper);
    }

    @Test
    public void testAssignRoleToMember() {
        Long memberId = 1L;


        List<Long> roleIds = List.of(1L, 2L, 3L);

        memberService.assignRoleToMember(memberId, roleIds);

        verify(memberRepository, times(roleIds.size())).assignRoleToMember(eq(memberId), any(Long.class));
    }

    @Test
    public void testFindMemberWithRoles() {
        Long memberId = 1L;
        Member member = new Member();
        when(memberRepository.findMemberWithRoles(memberId)).thenReturn(member);

        memberService.findMemberWithRoles(memberId);

        verify(requestMapper, times(1)).map(member);

    }


    @Test
    public void testDeleteRolesForMember() {
        Long memberId = 1L;

        memberService.deleteRolesForMember(memberId);

        verify(memberRepository, times(1)).deleteRolesForMember(memberId);
    }

    @Test
    public void testDeleteRoleForMember() {
        Long memberId = 1L;
        Long roleId = 1L;

        memberService.deleteRoleForMember(memberId, roleId);

        verify(memberRepository, times(1)).deleteRoleForMember(memberId, roleId);
    }

    @Test
    public void testUpdateOrInsertRole() {
        Long memberId = 1L;
        List<Long> existingRoles = List.of(1L, 2L);
        List<Long> newRoles = List.of(2L, 3L);

        Member memberWithRoles = new Member();
        memberWithRoles.setRoles(new ArrayList<>());
        for (Long roleId : existingRoles) {
            Role role = new Role();
            role.setId(roleId);
            memberWithRoles.getRoles().add(role);
        }

        when(memberRepository.findMemberWithRoles(memberId)).thenReturn(memberWithRoles);

        memberService.updateOrInsertRole(memberId, newRoles);

        verify(memberRepository, times(1)).assignRoleToMember(memberId, 3L);
        verify(memberRepository, times(1)).deleteRoleForMember(memberId, 1L);
    }

    @Test
    public void calculateAttendanceForPerson() {
        //given
        when(eventRepository.getTotalAttendanceByEventAndMember(any(), any())).thenReturn(5l);

        //when
        Long rs = memberService.calculateAttendanceForPerson(1l, 1);

        //then
        assertEquals(5, rs);
    }
}
