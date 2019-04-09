package com.example.jpademo.Controller;

import com.example.jpademo.domain.Worker;
import com.example.jpademo.repository.workerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class workerController {

    @Autowired
    private workerRepository wDao;

    @RequestMapping("/add")
    @ResponseBody
    public String addWorker(@RequestParam("name") String wName, @RequestParam("dependency") String wDependency){
        Worker worker = new Worker();
        worker.setName(wName);
        worker.setCome(true);
        worker.setDependency(wDependency);
        wDao.save(worker);
        return "add/addSuccess";
    }

    @RequestMapping("/show")
    public String showWorkers(){
        List<Worker> l = wDao.findAll();
        return "add/addSuccess";
    }
}
