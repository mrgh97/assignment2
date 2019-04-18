package com.example.jpademo.Controller;

import com.example.jpademo.Service.memberService;
import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
                              HttpServletRequest request,
                              Model model){
        HttpSession session=request.getSession();
        if(session.getAttribute("user")!=null){
            return "login/profile";
        }
        if(mService.checkLogin(userName,password)){
            Member member = mService.findMember(userName);
            session.setAttribute("user",member);
            return "redirect:/userCenter";
        }
        model.addAttribute("errMsg","Username or password error!");
        return "login/loginIndex";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute(value = "member") Member member,
                           BindingResult bindingResult,
                           @RequestParam("rePassword") String rePassword,
                           Model model,
                           HttpServletRequest request){
        if (bindingResult.hasErrors()) {
            return "login/register";
        }else if(!member.getPassword().equals(rePassword)){
            model.addAttribute("errMsg","Password not true!");
            return "login/register";
        }
        else{
            member.setSign("normal");
            mService.addMember(member);
            HttpSession session=request.getSession();
            //model.addAttribute("activePage","workers");
            session.setAttribute("user",member);
            return "redirect:/userCenter";
        }
    }
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("member",new Member());
        return "login/register";
    }

    @RequestMapping(value = "/userCenter",method = RequestMethod.GET)
    public String profile(){
        return "login/profile";
    }
//    @RequestMapping("/userCenter/edit/{userName}")
//    public String userEdit(@PathVariable String userName){
//
//        return "redirect:/userCenter";
//    }
}