package com.example.jpademo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jpademo.domain.Worker;
import com.example.jpademo.repository.WorkerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WorkerService {

	private WorkerRepository workerRepository;
	@Autowired
	public void setWorkerRepository(WorkerRepository w){
		this.workerRepository=w;
	}
//	@Transactional
//	public List<Worker> getAllWorkers() {
//		return (List<Worker>) workerRepository.findAll();
//	}

	@Transactional
	public Worker getById(Integer id) {
		return workerRepository.getOne(id);
	}

	@Transactional
	public void deleteWorker(Integer WorkerId) {
		workerRepository.deleteById(WorkerId);
	}

	@Transactional
	public void addWorker(Worker Worker) {
		workerRepository.save(Worker);
	}

	@Transactional
	public boolean updateWorker(Worker Worker) {
		return workerRepository.save(Worker) != null;
	}

	@Transactional
	public Page<Worker> getAllWorkers(Integer pageNum){
		Sort sort = new Sort(Sort.Direction.ASC,"id");
		Pageable pageable=new PageRequest(pageNum,5,sort);

		return this.workerRepository.findAll(pageable);
	}

	@Transactional
	public List<Worker> getAll() {
		return this.workerRepository.findAll();
	}
}
