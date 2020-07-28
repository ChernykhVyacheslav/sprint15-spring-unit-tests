package com.softserve.sprint14;

import com.softserve.sprint14.entity.Marathon;
import com.softserve.sprint14.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sprint14SpringMVCApplication implements CommandLineRunner {

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
        SpringApplication.run(Sprint14SpringMVCApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running Spring Boot Application");
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathonService.createOrUpdateMarathon(marathon);
        Marathon marathon2 = new Marathon();
        marathon2.setTitle("Marathon2");
        marathonService.createOrUpdateMarathon(marathon2);
    }
}
