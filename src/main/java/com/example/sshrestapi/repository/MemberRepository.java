package com.example.sshrestapi.repository;

import com.example.sshrestapi.entity.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
}
