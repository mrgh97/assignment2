package com.example.jpademo.repository;


import com.example.jpademo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface memberRepository extends JpaRepository<Member,Integer> {
    public List<Member> findByUserNameAndPassword(String name,String password);
}
