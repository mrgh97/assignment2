package com.example.jpademo.repository;

import com.example.jpademo.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface workerRepository extends JpaRepository<Worker,Integer>{
}
