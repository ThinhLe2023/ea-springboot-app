package edu.miu.cs.cs544.controller;

import feign.Param;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.MemberService;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.contract.RolePayload;
import edu.miu.cs.cs544.service.contract.RolesPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController extends BaseReadWriteController<MemberPayload, Member, Long> {

    @Autowired
    MemberService memberService;

    @PostMapping("/{memberId}/roles")
    public ResponseEntity<?> assignRoleToMember(@PathVariable("memberId") Long member_id, @RequestBody RolesPayload roles) {

        List<Long> role_ids = roles.getRoles().stream().map(RolePayload::getId)
                .toList();
        memberService.assignRoleToMember(member_id, role_ids);
        return response(() -> {
            return null;
        });
    }

    @GetMapping("/{memberId}/roles")
    public ResponseEntity<?> findMemberWithRoles(@PathVariable("memberId") Long member_id) {
        return response(() -> {
            return memberService.findMemberWithRoles(member_id);
        });
    }

    @DeleteMapping("/{memberId}/roles")
    public ResponseEntity<?> deleteRolesForMember(@PathVariable("memberId") Long member_id) {
        return response(() -> {
             memberService.deleteRolesForMember(member_id);
            return null;
        });
    }

    @DeleteMapping("/{memberId}/roles/{roleId}")
    public ResponseEntity<?> deleteRoleForMember(@PathVariable("memberId") Long member_id, @PathVariable("roleId") Long role_id) {
        return response(() -> {
            memberService.deleteRoleForMember(member_id, role_id);
            return null;
        });
    }


    @PutMapping("/{memberId}/roles")
    public ResponseEntity<?> updateOrInsertRole(@PathVariable("memberId") Long member_id, @RequestBody RolesPayload roles) {
        List<Long> role_ids = roles.getRoles().stream().map(RolePayload::getId)
                .toList();


        memberService.updateOrInsertRole(member_id, role_ids);
        return response(() -> {
            return null;
        });
    }


    @GetMapping("/{memberId}/events/{eventId}/attendance")
    public ResponseEntity<Long> calculateAttendanceForPerson(@PathVariable Long memberId, @PathVariable Integer eventId){
        return ResponseEntity.ok(memberService.calculateAttendanceForPerson(memberId, eventId));
    }

}
