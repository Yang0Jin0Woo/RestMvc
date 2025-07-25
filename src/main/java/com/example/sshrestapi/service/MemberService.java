package com.example.sshrestapi.service;

import com.example.sshrestapi.dto.MemberDto;
import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.repository.MemberEMRepository;
import com.example.sshrestapi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    //private final MemberRepository memberRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    private final MemberEMRepository memberRepository;

    public MemberService(MemberEMRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("엔티티가 존재하지 않습니다."));
    }

    @Transactional
    public Member create(MemberDto dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        return memberRepository.save(member);
    }

    @Transactional
    public Member update(Long id, MemberDto dto) {
        Member member = findById(id);
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        return memberRepository.save(member);
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("엔티티가 존재하지 않습니다.");
        }
        memberRepository.deleteById(id);
    }
}
