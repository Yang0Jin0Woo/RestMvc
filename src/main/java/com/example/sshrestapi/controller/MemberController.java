package com.example.sshrestapi.controller;

import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 페이지 조회
    @GetMapping("/page")
    public ResponseEntity<Page<Member>> getPagedMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Member> pagedResult = memberService.findPage(pageable);
        return ResponseEntity.ok(pagedResult);
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<Member>> getAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Member> getOne(@PathVariable Long id) {
        Member oneMember = memberService.findById(id);
        return ResponseEntity.ok(oneMember);
    }

    // 생성
    @PostMapping
    public ResponseEntity<Member> create(@RequestBody Member member) {
        member.setId(null);
        Member saved = memberService.save(member);
        return ResponseEntity
                .created(URI.create("/api/members/" + saved.getId()))
                .body(saved);
    }

    // 전체 교체(PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id,
                                         @RequestBody Member member) {
        member.setId(id);
        Member updated = memberService.update(id,member);
        return ResponseEntity.ok(updated);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}