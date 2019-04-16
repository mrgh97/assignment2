package com.example.jpademo.Controller;

import com.example.jpademo.Service.memberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class loginController {
    private memberService mService;

    @Autowired
    public void setmService(memberService m){
        this.mService=m;
    }
}
