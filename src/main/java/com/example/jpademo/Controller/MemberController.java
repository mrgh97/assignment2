package com.example.jpademo.Controller;


import com.example.jpademo.Service.MemberService;
import com.example.jpademo.Service.WorkerService;
import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.MemberWorker;
import com.example.jpademo.domain.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;
    private WorkerService workerService;

    @Autowired
    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(value = {"","/"})
    public String getMemberAndWorkers(Model model){
        return "member/index";
    }
    @GetMapping("/select/{workerId}")
    public String selectWorker(@PathVariable Integer workerId, HttpServletRequest request){
        Worker worker=workerService.getById(workerId);

        MemberWorker memberWorker=new MemberWorker();
        memberWorker.setWorker(worker);
        Member member= (Member) request.getSession().getAttribute("user");
        memberWorker.setMember(member);
        member.setMemberWorker(memberWorker);

        memberService.updateMember(member);

        return "login/profile";
    }
}
