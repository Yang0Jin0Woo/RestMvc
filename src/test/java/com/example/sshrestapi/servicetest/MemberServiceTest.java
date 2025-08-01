package com.example.sshrestapi.servicetest;

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
    void 회원_정보_생성() {
        // given
        Member member = new Member("Alice", "alice@example.com");

        // when
        Member saved = memberService.save(member);

        // then
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void 회원_단일_조회() {
        // given
        Member member = new Member("Alice", "alice@example.com");
        Member saved = memberService.save(member);

        // when
        Member found = memberService.findById(saved.getId());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Alice");
    }

    @Test
    void 회원_리스트_조회() {
        // given
        memberService.save(new Member("Bob", "bob@example.com"));
        memberService.save(new Member("Carol", "carol@example.com"));

        // when
        List<Member> all = memberService.findAll();

        // then
        assertThat(all).hasSize(2)
                .extracting("email")
                .containsExactlyInAnyOrder("bob@example.com", "carol@example.com");
    }

    @Test
    void 회원_정보_수정() {
        // given
        Member member = new Member("Dave", "dave@example.com");
        Member saved = memberService.save(member);

        // when
        Member updateData = new Member("Smith", "smith@example.com");
        Member updated = memberService.update(saved.getId(), updateData);

        // then
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("Smith");
        assertThat(updated.getEmail()).isEqualTo("smith@example.com");
    }

    @Test
    void 회원_정보_삭제() {
        // given
        Member member = new Member("Eve", "eve@example.com");
        Member saved = memberService.save(member);

        // when
        memberService.delete(saved.getId());

        // then
        assertThat(memberRepository.existsById(saved.getId())).isFalse();
    }
}
