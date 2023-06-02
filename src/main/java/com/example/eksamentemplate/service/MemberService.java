package com.example.eksamentemplate.service;

import com.example.eksamentemplate.exception.ResourceNotFoundException;
import com.example.eksamentemplate.model.Member;
import com.example.eksamentemplate.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepo memberRepo;
    @Autowired
    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }
    //GET all
    public List<Member> getAll() {
        return memberRepo.findAll();
    }
    //GET by id
    public ResponseEntity<Member> getById(Integer id) {
        if (memberRepo.findById(id).isPresent()){
            return new ResponseEntity<>(memberRepo.findById(id).get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Medlem med id " + id + " blev ikke fundet");
        }
    }
    //POST
    public ResponseEntity<Member> create(Member member) {
        //post er non-idempotent, så tjek om id findes i forvejen
        try {
            memberRepo.save(member);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //kan smide sin egen her
        } //man får den præcise fejl i fejlbesked
        return new ResponseEntity<>(memberRepo.save(member), HttpStatus.CREATED);
    }
    //PUT
    public ResponseEntity<Member> update(Member updatedMember) {
        //PUT er idempotent og skal derfor tjekke id, for at den opdatere en der allerede eksisterer
        int id = updatedMember.getMemberId();
        if (memberRepo.findById(id).isPresent()){
            return new ResponseEntity<>(memberRepo.save(updatedMember), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Medlem med id " + id + " blev ikke fundet");
        }
    }
    //DELETE
    public ResponseEntity<Member> delete(Integer id) {
        if (memberRepo.findById(id).isPresent()){
            Member member = memberRepo.findById(id).get();
            memberRepo.deleteById(id);
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Medlem med id " + id + " blev ikke fundet");
        }
    }
    //COUNT
    public Integer countAll(){
        System.out.println(memberRepo.countAll());
        return memberRepo.countAll();
    }
}
