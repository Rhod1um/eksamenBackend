package com.example.eksamentemplate.repository;

import com.example.eksamentemplate.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepo extends JpaRepository<Member, Integer> {
    @Query(value="select COUNT(memberid) from member", nativeQuery=true)
    Integer countAll();
}
