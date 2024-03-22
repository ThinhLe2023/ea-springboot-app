package edu.miu.cs.cs544.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.controller.MemberController;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.MemberService;
import edu.miu.cs.cs544.service.MemberServiceImpl;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.contract.RolePayload;
import edu.miu.cs.cs544.service.contract.RolesPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.mockito.Mockito.*;
public class MemberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
        memberController = new MemberController(memberService);
    }

    @Test
    public void testAssignRoleToMember() throws Exception {
        Long memberId = 1L;

        RolePayload rolePayload1 = new RolePayload();
        rolePayload1.setId(1L);

        RolePayload rolePayload2 = new RolePayload();
        rolePayload2.setId(2L);

        RolesPayload rolesPayload = new RolesPayload();
        rolesPayload.setRoles(Arrays.asList(rolePayload1, rolePayload2));

        mockMvc.perform(post("/members/{memberId}/roles", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rolesPayload)))
                .andExpect(status().isOk());

        List<Long> roleIds = rolesPayload.getRoles().stream().map(RolePayload::getId).toList();
        verify(memberService, times(1)).assignRoleToMember(eq(memberId), eq(roleIds));
    }

    @Test
    public void testFindMemberWithRoles() throws Exception {
        Long memberId = 1L;
        MemberPayload member = new MemberPayload();
        member.setId(memberId);

        // Mocking the response of memberService.findMemberWithRoles()
        when(memberService.findMemberWithRoles(eq(memberId)))
                .thenReturn(member);

        mockMvc.perform(get("/members/{memberId}/roles", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId));

    }

    @Test
    public void testDeleteRolesForMember() throws Exception {
        Long memberId = 1L;

        mockMvc.perform(delete("/members/{memberId}/roles", memberId))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRoleForMember() throws Exception {
        Long memberId = 1L;
        Long roleId = 1L;

        mockMvc.perform(delete("/members/{memberId}/roles/{roleId}", memberId, roleId))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateOrInsertRole() throws Exception {
        Long memberId = 1L;
        RolesPayload rolesPayload = new RolesPayload();
        RolePayload rolePayload1 = new RolePayload();
        rolePayload1.setId(1L);

        RolePayload rolePayload2 = new RolePayload();
        rolePayload2.setId(2L);
        rolesPayload.setRoles(Arrays.asList(rolePayload1, rolePayload2));

        mockMvc.perform(put("/members/{memberId}/roles", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rolesPayload)))
                .andExpect(status().isOk());
    }
}
