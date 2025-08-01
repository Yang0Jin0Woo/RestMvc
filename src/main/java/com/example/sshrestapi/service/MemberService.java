package com.example.sshrestapi.service;

import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Page<Member> findPage(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member 찾을 수 없음. " + id));
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public Member update(Long id, Member updateData) {
        Member existing = findById(id);
        existing.setName(updateData.getName());
        existing.setEmail(updateData.getEmail());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new EntityNotFoundException("Member 찾을 수 없음. " + id);
        }
        memberRepository.deleteById(id);
    }
}