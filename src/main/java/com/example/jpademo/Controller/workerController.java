package com.example.jpademo.Controller;

import com.example.jpademo.Service.workerService;
import com.example.jpademo.domain.Worker;
import com.example.jpademo.repository.workerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/workers")
public class workerController {

    private workerService workerService;

    @Autowired
    public void setWorkerService(workerService workerService) {
        this.workerService = workerService;
    }

    @RequestMapping(value = {"","/"})
    public String index(Model model){
        model.addAttribute("activePage","workers");
        model.addAttribute("workers",this.workerService.getAllWorkers());
        return "workerCrud/index";
    }

    @RequestMapping(value="/add",method = RequestMethod.GET)
    public String addWorker(Model model){
        model.addAttribute("activePage","workers");
        model.addAttribute("worker",new Worker());
        return "workerCrud/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addWorker(@Valid @ModelAttribute(value = "worker") Worker worker, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "workers");
            return "workerCrud/add";
        }

        this.workerService.addWorker(worker);
        return "redirect:/workers";
    }
    @RequestMapping("/show")
    public String showWorkers(){
        return "redirect:/workers";
    }
}
