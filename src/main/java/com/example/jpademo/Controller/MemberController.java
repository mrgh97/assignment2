package com.example.jpademo.Controller;


import com.example.jpademo.Controller.util.HeaderUtil;
import com.example.jpademo.Controller.util.PaginationUtil;
import com.example.jpademo.Service.MemberService;
import com.example.jpademo.Service.WorkerService;
import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.Worker;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.Header;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/member")
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

    @GetMapping("")
    public ResponseEntity<List<Member>> getMemberAndWorkers(@RequestParam(name = "page", defaultValue = "1") int page) {
        log.debug("Get all members.");
        Pageable pageable = new PageRequest(page - 1, 5);
        Page<Member> members;
        System.out.println(pageable.getPageSize());
        if (redisTemplate.opsForValue().get("members" + "_" + pageable.getPageNumber()) != null) {
            members = (Page<Member>) redisTemplate.opsForValue().get("members" + "_" + pageable.getPageNumber());
        } else {
            members = this.memberService.getMembers(pageable);
            redisTemplate.opsForValue().set("members" + "_" + pageable.getPageNumber(), members);
        }

        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(members, "/api/members"))
                .body(members.getContent());
    }

    @GetMapping("/select/{workerId}")
    public ResponseEntity<Worker> selectWorker(@PathVariable Integer workerId, HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("user");
        this.memberService.addWorker(member, workerId);
        //this.workerService.addMember(member, workerId);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("Select a worker successfully!", member.getUserName()))
                .body(this.workerService.getById(workerId));
    }
}
