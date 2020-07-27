package com.softserve.sprint13;

import com.softserve.sprint13.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sprint13HibernateWithSpringApplication implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;
    @Autowired
    MarathonService marathonService;
    @Autowired
    SprintService sprintService;
    @Autowired
    ProgressService progressService;

    public static void main(String[] args) {
        SpringApplication.run(Sprint13HibernateWithSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running Spring Boot Application");
    }
}
