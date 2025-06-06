package com.example.member.domain.user.repository;

import com.example.member.domain.user.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
//    이메일로 회원 정보 조회(select * from member_table)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

}
