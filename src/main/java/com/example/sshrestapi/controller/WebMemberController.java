package com.example.sshrestapi.controller;

import com.example.sshrestapi.entity.Member;
import com.example.sshrestapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class WebMemberController {

    private final MemberService memberService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Model model) {

        Page<Member> memberPage = memberService.findPage(PageRequest.of(page, size));

        if (memberPage.getContent().isEmpty() && page > 0) {
            return "redirect:/members?page=0&size=" + size;
        }

        model.addAttribute("memberPage", memberPage);
        return "members/list";
    }

//    @GetMapping
//    public String list(Model model) {
//        model.addAttribute("members", memberService.findAll());
//        return "members/list";
//    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "members/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("member", new Member());
        return "members/form";
    }

    @PostMapping
    public String create(@ModelAttribute Member member) {
        memberService.save(member);
        return "redirect:/members";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "members/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Member member) {
        memberService.update(id, member);
        return "redirect:/members/"+id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/members";
    }
}