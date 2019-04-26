package com.example.jpademo.Service;

import com.example.jpademo.domain.Member;
import com.example.jpademo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private MemberRepository mRepository;

    @Autowired
    public void setmRepository(MemberRepository m){
        this.mRepository=m;
    }
    @Transactional
    public void addMember(Member member){
        mRepository.save(member);
    }
    @Transactional
    public boolean  updateMember(Member member){return mRepository.save(member)!=null;}
    @Transactional
    public void deleteMember(Integer id){
        mRepository.deleteById(id);
    }
    @Transactional
    public Member findMember(String userName){
        Member member;
        member=mRepository.findByUserName(userName);
        return member;
    }
    @Transactional
    public boolean checkLogin(String userName,String password){
        return mRepository.findByUserNameAndPassword(userName,password).size()>0;
    }
}
