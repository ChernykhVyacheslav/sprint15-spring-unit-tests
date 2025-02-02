package com.softserve.sprint15;

import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.Sprint;
import com.softserve.sprint15.entity.Task;
import com.softserve.sprint15.entity.User;
import com.softserve.sprint15.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Profile("!test")
public class Sprint15SpringUnitTests implements CommandLineRunner {

    UserService userService;

    TaskService taskService;

    MarathonService marathonService;

    SprintService sprintService;

    ProgressService progressService;

    @Autowired
    public Sprint15SpringUnitTests(UserService userService, TaskService taskService,
                                   MarathonService marathonService, SprintService sprintService,
                                   ProgressService progressService) {
        this.userService = userService;
        this.taskService = taskService;
        this.marathonService = marathonService;
        this.sprintService = sprintService;
        this.progressService = progressService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Sprint15SpringUnitTests.class, args);
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
        List<User> trainees = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Sprint sprint = new Sprint();
            sprint.setTitle("Sprint" + i);
            sprint.setStartDate(LocalDate.now());
            sprint.setFinishDate(LocalDate.now().plusMonths(3));
            sprintService.createOrUpdateSprint(sprint);
            sprintService.addSprintToMarathon(sprint, marathon);
            Sprint sprint2 = new Sprint();
            sprint2.setTitle("Sprint" + (i + 2));
            sprint2.setStartDate(LocalDate.now());
            sprint2.setFinishDate(LocalDate.now().plusMonths(6));
            sprintService.createOrUpdateSprint(sprint2);
            sprintService.addSprintToMarathon(sprint2, marathon2);

            for (int j = 0; j < 2; j++) {
                Task task = new Task();
                task.setTitle("Task" + i + j);
                taskService.createOrUpdateTask(task);
                taskService.addTaskToSprint(task, sprintService.getSprintById(i + 1L));
                tasks.add(task);
            }
        }
        for (int i = 0; i < 2; i++) {
            User mentor = new User();
            mentor.setEmail("mentoruser" + i + "@dh.com");
            mentor.setFirstName("MentorName" + i);
            mentor.setLastName("MentorSurname" + i);
            mentor.setPassword("qwertyqwerty" + i);
            mentor.setRole(User.Role.MENTOR);
            mentor = userService.createOrUpdateUser(mentor);
            userService.addUserToMarathon(mentor, marathon);

            User trainee = new User();
            trainee.setEmail("traineeUser" + i + "@dh.com");
            trainee.setFirstName("TraineeName" + i);
            trainee.setLastName("TraineeSurname" + i);
            trainee.setPassword("qwerty^qwerty" + i);
            trainee.setRole(User.Role.TRAINEE);
            userService.createOrUpdateUser(trainee);
            userService.addUserToMarathon(trainee, marathon);
            trainees.add(trainee);
        }
        for (Task task:
              tasks) {
            for (User trainee:
                 trainees) {
                progressService.addTaskForStudent(task, trainee);
            }
        }
    }
}
