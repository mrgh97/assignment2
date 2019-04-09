package com.example.jpademo;

import com.example.jpademo.domain.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpademoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private JpaRepository jpaRepository;

    @Test
    public void insert() {
    }

}
