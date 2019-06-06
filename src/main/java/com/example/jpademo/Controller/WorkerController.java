package com.example.jpademo.Controller;

import com.example.jpademo.Controller.util.HeaderUtil;
import com.example.jpademo.Controller.util.PaginationUtil;
import com.example.jpademo.Service.WorkerService;
import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.Worker;
import com.example.jpademo.repository.WorkerRepository;
import io.swagger.annotations.Api;
import org.apache.tomcat.util.http.ResponseUtil;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final Logger log = LoggerFactory.getLogger(WorkerController.class);

    private String ENTITY_NAME = "Worker";

    private WorkerService workerService;

    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    @Autowired
    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/index")
    public ResponseEntity<List<Worker>> getAllWorkers(@RequestParam(name = "page", defaultValue = "1") int page) {
        log.debug("REST request to get a page of Employees");
        Pageable pageable=new PageRequest(page,5);
        Page<Worker> workers;
        if (redisTemplate.opsForValue().get("worker_" + pageable.getPageNumber()) == null) {
            workers = workerService.getAll(pageable);
            redisTemplate.opsForValue().set("worker_"+pageable.getPageNumber(),workers);
        } else {
            workers  = (Page<Worker>) redisTemplate.opsForValue().get("worker_"+pageable.getPageNumber());
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(workers, "/api/workers");
        return ResponseEntity.ok().headers(headers).body(workers.getContent());
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Worker> addWorker(@RequestBody Worker worker, BindingResult bindingResult) throws URISyntaxException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createAlert("Input message has errors.", worker.getId().toString()))
                    .build();
        }

        this.workerService.addWorker(worker);
        return ResponseEntity.created(new URI("/api/workers/view/" + worker.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, worker.getId().toString()))
                .body(worker);
    }

//    @RequestMapping(value = "/edit/{id}")
//    public String editWorker(@PathVariable Integer id, Model model) {
//        model.addAttribute("worker", workerService.getById(id));
//        model.addAttribute("activePage", "workers");
//        return "workerCrud/edit";
//    }

    @PostMapping("/update")
    public ResponseEntity<Worker> updateWorker(@RequestBody Worker worker) throws URISyntaxException {
        log.debug("Rest to update workers.");
        workerService.updateWorker(worker);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, worker.getId().toString()))
                .body(worker);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ResponseEntity<Worker> viewWorker(@PathVariable Integer id) {
        if (workerService.getById(id) == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("This worker is not existed.", id.toString())).build();
        }
        return ResponseEntity.ok().body(workerService.getById(id));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Void> deleteWorker(@PathVariable Integer id) {
        workerService.deleteWorker(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
