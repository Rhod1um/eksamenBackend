package com.example.eksamentemplate.controller;

import com.example.eksamentemplate.model.Boat;
import com.example.eksamentemplate.model.Member;
import com.example.eksamentemplate.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RestControllerAdvice
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping //med og uden slash til sidst i url er ikke længere det samme i spring boot
    public List<Member> getAll(){
        return memberService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Member> getOne(@PathVariable Integer id){
        return memberService.getById(id);
    }
    @PostMapping
    public ResponseEntity<Member> create(@RequestBody Member member){
        return memberService.create(member);
    }
    @PutMapping
    public ResponseEntity<Member> update(@RequestBody Member member){
        return memberService.update(member);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Member> delete(@PathVariable Integer id){
        return memberService.delete(id);
    }
    @GetMapping("/count")
    public Integer countAll(){
        return memberService.countAll();
    }
}
