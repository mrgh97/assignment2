package com.example.jpademo.Service;

import com.example.jpademo.domain.Member;
import com.example.jpademo.repository.memberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class memberService {

    private memberRepository mRepository;

    @Autowired
    public void setmRepository(memberRepository m){
        this.mRepository=m;
    }
    public void addMember(Member member){
        mRepository.save(member);
    }
    public void deleteMember(Integer id){
        mRepository.deleteById(id);
    }

    public Member findMember(String userName){
        Member member;
        member=mRepository.findByUserName(userName);
        return member;
    }

    public boolean checkLogin(String userName,String password){
        return mRepository.findByUserNameAndPassword(userName,password).size()>0;
    }
}
