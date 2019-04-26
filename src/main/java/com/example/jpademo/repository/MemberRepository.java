package com.example.jpademo.repository;


import com.example.jpademo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Integer> {
    public Member findByUserName(String userName);
    public List<Member> findByUserNameAndPassword(String userName,String password);
}
