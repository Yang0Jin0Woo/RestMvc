package com.example.sshrestapi.controller;

import com.example.sshrestapi.dto.MemberDto;
import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class WebMemberController {

    private final MemberService memberService;

    @Autowired
    public WebMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        MemberDto memberDto = new MemberDto();
        model.addAttribute("memberDto", memberDto);
        return "members/form";
    }

    @PostMapping
    public String create(@ModelAttribute MemberDto memberDto) {
        memberService.create(memberDto);
        return "redirect:/members";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "members/view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member m = memberService.findById(id);
        MemberDto dto = MemberDto.builder()
                .name(m.getName())
                .email(m.getEmail())
                .build();
        model.addAttribute("memberDto", dto);
        model.addAttribute("id", id);
        return "members/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute MemberDto memberDto) {
        memberService.update(id, memberDto);
        return "redirect:/members";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/members";
    }
}
