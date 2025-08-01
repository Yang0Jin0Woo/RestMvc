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

    @GetMapping("/page")
    public ResponseEntity<Page<Member>> getPagedMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Member> pagedResult = memberService.findPage(pageable);
        return ResponseEntity.ok(pagedResult);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getOne(@PathVariable Long id) {
        Member oneMember = memberService.findById(id);
        return ResponseEntity.ok(oneMember);
    }

    @PostMapping
    public ResponseEntity<Member> create(@RequestBody Member member) {
        Member saved = memberService.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id,
                                         @RequestBody Member member) {
        Member updated = memberService.update(id,member);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}