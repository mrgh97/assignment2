package com.example.jpademo.Controller;


import com.example.jpademo.Service.MemberService;
import com.example.jpademo.Service.WorkerService;
import com.example.jpademo.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = {"","/","/index"})
    public String getMemberAndWorkers(Model model,@RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum){
        model.addAttribute("activePage","members");
        model.addAttribute("totalPage",(this.memberService.findAll().size()/5));
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("nextPage",pageNum+1);
        model.addAttribute("prePage",pageNum-1);
        model.addAttribute("members",this.memberService.getMembers(pageNum));
        return "member/index";
    }
    @GetMapping("/select/{workerId}")
    public String selectWorker(@PathVariable Integer workerId, HttpServletRequest request){
        Member member= (Member) request.getSession().getAttribute("user");
        this.memberService.addWorker(member,workerId);
        this.workerService.addMember(member,workerId);
        return "login/profile";
    }
}
