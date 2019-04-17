package com.example.jpademo.Controller;

import com.example.jpademo.Service.memberService;
import com.example.jpademo.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class loginController {
    private memberService mService;

    @Autowired
    public void setmService(memberService m){
        this.mService=m;
    }

    @RequestMapping(value={"","/"})
    public String index(Model model){
        model.addAttribute("activePage","home");
        return "index";
    }

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String login(Model model){
        return "login/loginIndex";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String varifyLogin(@RequestParam("userName") String userName,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model){

        if(mService.checkLogin(userName,password)){
            Member member = mService.findMember(userName);
            session.setAttribute("user",member);
            System.out.println("woziazhe!");
            return "login/profile";
        }
        model.addAttribute("errMsg","Username or password error!");
        return "login/loginIndex";
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("rePassword") String rePassword,
                           Model model,
                           HttpSession session){
        if(!userName.equals("") && !password.equals("")&&password.equals(rePassword)){
            Member member=new Member();
            member.setUserName(userName);
            member.setPassword(password);
            mService.addMember(member);
            //model.addAttribute("activePage","workers");
            session.setAttribute("user",member);
            return "login/profile";
        }else{
            model.addAttribute("errMsg","Username or password error!");
            return "login/register";
        }
    }
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "login/register";
    }
}