package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberRepository extends BaseRepository<Member, Long>, JpaRepository<Member, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Member_Role (member_id, role_id) VALUES (:member_id, :role_id)", nativeQuery = true)
    void assignRoleToMember(@Param("member_id") Long member_id, @Param("role_id") Long role_id);


    @Query(value = "SELECT m.* FROM Member m JOIN Member_Role mr ON m.id = mr.member_id WHERE m.id = :memberId",
            nativeQuery = true)
    Member findMemberWithRoles(@Param("memberId") Long memberId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Member_Role WHERE member_id = :memberId", nativeQuery = true)
    void deleteRolesForMember(@Param("memberId") Long memberId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Member_Role WHERE member_id = :member_id AND role_id = :role_id", nativeQuery = true)
    void deleteRoleForMember(@Param("member_id") Long member_id, @Param("role_id") Long role_id);

    @Query("SELECT m FROM Member m JOIN m.accounts a WHERE a.balance < 5")
    List<Member> findMembersByAccountBalanceLessThanFive();


}
