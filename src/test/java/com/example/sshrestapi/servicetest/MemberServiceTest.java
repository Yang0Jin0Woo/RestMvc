package com.example.sshrestapi.servicetest;

import com.example.sshrestapi.dto.MemberDto;
import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.repository.MemberRepository;
import com.example.sshrestapi.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원_정보_생성(){
        // given
        MemberDto createDto = MemberDto.builder()
                .name("Alice")
                .email("alice@example.com")
                .build();
        // when
        Member saved = memberService.create(createDto);
        // then
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void 회원_단일_조회(){
        // given
        MemberDto dto = MemberDto.builder()
                .name("Alice")
                .email("alice@example.com")
                .build();
        Member saved = memberService.create(dto);
        // when
        Member found = memberService.findById(saved.getId());
        // then
        assertThat(found).isNotNull();
    }

    @Test
    void 회원_리스트_조회(){
        // given
        MemberDto dto1 = MemberDto.builder().name("Bob").email("bob@example.com").build();
        MemberDto dto2 = MemberDto.builder().name("Carol").email("carol@example.com").build();
        memberService.create(dto1);
        memberService.create(dto2);
        // when
        List<Member> all = memberService.findAll();
        // then
        assertThat(all).hasSize(2)
                .extracting("email")
                .containsExactlyInAnyOrder("bob@example.com", "carol@example.com");
    }

    @Test
    void 회원_정보_수정(){
        // given
        MemberDto createDto = MemberDto.builder()
                .name("Dave")
                .email("dave@example.com")
                .build();
        Member saved = memberService.create(createDto);
        // when
        MemberDto updateDto = MemberDto.builder()
                .name("Smith")
                .email("smith@example.com")
                .build();
        Member updated = memberService.update(saved.getId(), updateDto);
        // then
        assertThat(updated.getName()).isEqualTo("Smith");
        assertThat(updated.getEmail()).isEqualTo("smith@example.com");
    }

    @Test
    void 회원_정보_삭제(){
        // given
        MemberDto dto = MemberDto.builder()
                .name("Eve")
                .email("eve@example.com")
                .build();
        Member saved = memberService.create(dto);
        // when
        memberService.delete(saved.getId());
        // then
        assertThat(memberRepository.existsById(saved.getId())).isFalse();
    }
}
