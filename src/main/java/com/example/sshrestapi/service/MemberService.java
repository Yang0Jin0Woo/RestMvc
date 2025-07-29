package com.example.sshrestapi.service;

import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 전체 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 단건 조회
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    // 생성
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    // 수정
    public Optional<Member> update(Long id, Member memberData) {
        return memberRepository.findById(id)
                .map(existing -> {
                    existing.setName(memberData.getName());
                    existing.setEmail(memberData.getEmail());
                    return memberRepository.save(existing);
                });
    }

    // 삭제
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}