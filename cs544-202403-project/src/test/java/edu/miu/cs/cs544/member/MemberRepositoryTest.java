package edu.miu.cs.cs544.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
public class MemberRepositoryTest {

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void testAssignRoleToMember() {
        Long memberId = 1L;
        Long roleId = 2L;

        memberRepository.assignRoleToMember(memberId, roleId);

        verify(memberRepository).assignRoleToMember(memberId, roleId);
    }

    @Test
    public void testFindMemberWithRoles() {
        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);

        when(memberRepository.findMemberWithRoles(memberId)).thenReturn(member);

        Member foundMember = memberRepository.findMemberWithRoles(memberId);

        verify(memberRepository).findMemberWithRoles(memberId);
        assertEquals(memberId, foundMember.getId());
    }

    @Test
    public void testDeleteRolesForMember() {
        Long memberId = 1L;

        memberRepository.deleteRolesForMember(memberId);

        verify(memberRepository).deleteRolesForMember(memberId);
    }

    @Test
    public void testDeleteRoleForMember() {
        Long memberId = 1L;
        Long roleId = 2L;

        memberRepository.deleteRoleForMember(memberId, roleId);

        verify(memberRepository).deleteRoleForMember(memberId, roleId);
    }
}
