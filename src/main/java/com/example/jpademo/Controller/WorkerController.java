package com.example.jpademo.Controller;

import com.example.jpademo.Service.WorkerService;
import com.example.jpademo.domain.Worker;
import com.example.jpademo.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;

@Controller
@RequestMapping("/workers")
public class WorkerController {

    private WorkerService workerService;

    @Autowired
    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    @RequestMapping(value = {"","/","/index"})
    public String index(Model model,@RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum){
        model.addAttribute("activePage","workers");
        model.addAttribute("totalPage",(this.workerService.getAll().size()/5));
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("nextPage",pageNum+1);
        model.addAttribute("prePage",pageNum-1);
        model.addAttribute("workers",this.workerService.getAllWorkers(pageNum));
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

    @RequestMapping(value = "/edit/{id}")
    public String editWorker(@PathVariable Integer id,Model model){
        model.addAttribute("worker",workerService.getById(id));
        model.addAttribute("activePage","workers");
        return "workerCrud/edit";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateWorker(@Valid @ModelAttribute(value = "worker") Worker worker,Model model){
        model.addAttribute("activePage","workers");
        workerService.updateWorker(worker);
        return  "redirect:/workers/view/"+worker.getId();
    }

    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    public String viewWorker(@PathVariable Integer id,Model model){
        model.addAttribute("worker",workerService.getById(id));
        model.addAttribute("activePage","workers");
        return "workerCrud/view";
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public String deleteWorker(@PathVariable Integer id,Model model){
        model.addAttribute("activePage","workers");
        workerService.deleteWorker(id);
        return "redirect:/workers";
    }
}
