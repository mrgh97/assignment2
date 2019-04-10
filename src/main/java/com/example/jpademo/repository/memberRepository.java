package com.example.jpademo.repository;


import com.example.jpademo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface memberRepository extends JpaRepository<Member,Integer> {
}
