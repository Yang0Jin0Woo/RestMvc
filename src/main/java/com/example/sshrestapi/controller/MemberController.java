package com.example.sshrestapi.controller;

import com.example.sshrestapi.dto.MemberDto;
import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAll() {
        List<Member> members = memberService.findAll();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getOne(@PathVariable Long id) {
        Member member = memberService.findById(id);
        return ResponseEntity.ok(member);
    }

    @PostMapping
    public ResponseEntity<Member> create(@RequestBody MemberDto dto) {
        Member created = memberService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/members/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody MemberDto dto) {
        Member updated = memberService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
