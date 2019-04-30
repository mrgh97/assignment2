package com.example.jpademo.Controller;


import com.example.jpademo.Service.MemberService;
import com.example.jpademo.Service.WorkerService;
import com.example.jpademo.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Controller
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;
    private WorkerService workerService;

    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    @Autowired
    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping(value = {"", "/", "/index"})
    public String getMemberAndWorkers(Model model, @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
        log.debug("Get all members.");
        if(redisTemplate.opsForValue().get("members"+"_"+pageNum)!=null){
            model.addAttribute("members",redisTemplate.opsForValue().get("members"+"_"+pageNum));
            model.addAttribute("activePage", "members");
            model.addAttribute("totalPage", redisTemplate.opsForValue().get("member_total_page"));
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("nextPage", pageNum + 1);
            model.addAttribute("prePage", pageNum - 1);
            System.out.println();
        }else{
            model.addAttribute("activePage", "members");
            model.addAttribute("totalPage", (this.memberService.findAll().size() / 5));
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("nextPage", pageNum + 1);
            model.addAttribute("prePage", pageNum - 1);
            model.addAttribute("members", this.memberService.getMembers(pageNum));
            redisTemplate.opsForValue().set("members"+"_"+pageNum, this.memberService.getMembers(pageNum));
            redisTemplate.opsForValue().set("member_total_page", (this.memberService.findAll().size() / 5));
        }

        return "member/index";
    }

    @GetMapping("/select/{workerId}")
    public String selectWorker(@PathVariable Integer workerId, HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("user");
        this.memberService.addWorker(member, workerId);
        this.workerService.addMember(member, workerId);
        return "login/profile";
    }
}
