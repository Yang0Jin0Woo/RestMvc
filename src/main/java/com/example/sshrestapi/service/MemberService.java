package com.example.sshrestapi.service;

import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Page<Member> findPage(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> update(Long id, Member memberData) {
        return memberRepository.findById(id)
                .map(existing -> {
                    existing.setName(memberData.getName());
                    existing.setEmail(memberData.getEmail());
                    return existing;
                });
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}