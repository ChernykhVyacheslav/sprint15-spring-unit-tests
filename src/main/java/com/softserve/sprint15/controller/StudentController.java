package com.softserve.sprint15.controller;

import com.softserve.sprint15.dto.MarathonDTO;
import com.softserve.sprint15.dto.UserDTO;
import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.User;
import com.softserve.sprint15.service.MarathonService;
import com.softserve.sprint15.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    private MarathonService marathonService;

    private UserService userService;

    @Autowired
    public StudentController(MarathonService marathonService, UserService userService) {
        logger.info("Initializing StudentController");
        this.marathonService = marathonService;
        this.userService = userService;
    }

    @GetMapping("/{userId}/addMarathons/")
    public String addMarathonsToUsersForm(Model theModel, @PathVariable Long userId) {
        logger.info("Show form for adding marathons to user #" + userId);

        User user = userService.getUserById(userId);

        theModel.addAttribute("user", user);

        theModel.addAttribute("marathons", marathonService.getAll());

        return "students/add-marathons-to-students-form";
    }

    @PostMapping("/{userId}/add-marathons-to-student")
    public String addMarathonsToUser(
            @ModelAttribute("user") UserDTO user, @PathVariable Long userId,
            BindingResult bindingResult) {
        logger.info("Add marathons to user #" + userId);
        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getAllErrors().toString());
            return "students/add-marathons-to-students-form";
        } else {
            User tempUser = userService.getUserById(userId);
            for (Long marathonId :
                    user.getMarathonIds()) {
                userService.addUserToMarathon(tempUser, marathonService.getMarathonById(marathonId));
            }
            logger.info("Added successfully!");
            return "redirect:/students";
        }
    }

    @GetMapping("/{marathonId}/add")
    public String addUsersForm(Model theModel, @PathVariable Long marathonId) {
        logger.info("Show form for adding users to marathon #" + marathonId);

        Marathon theMarathon = marathonService.getMarathonById(marathonId);

        theModel.addAttribute("marathon", theMarathon);

        theModel.addAttribute("users", userService.getAllByRole(User.Role.TRAINEE.toString()));

        return "students/add-students-form";
    }

    @PostMapping("/{marathonId}/add")
    public String addUsersToMarathon(
            @ModelAttribute("marathon") MarathonDTO marathon, @PathVariable Long marathonId,
            BindingResult bindingResult) {
        logger.info("Add user to marathon #" + marathonId);
        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getAllErrors().toString());
            return "students/add-students-form";
        } else {
            Marathon tempMarathon = marathonService.getMarathonById(marathonId);
            for (Long userId :
                    marathon.getUserIds()) {
                userService.addUserToMarathon(userService.getUserById(userId), tempMarathon);
            }
            logger.info("Added successfully!");
            return "redirect:/marathons";
        }
    }

    @GetMapping
    public String listStudents(Model theModel) {
        logger.info("Get list of all students");
        theModel.addAttribute("students",
                userService.getAllByRole(User.Role.TRAINEE.toString()));
        return "students/list-students";
    }

    @GetMapping("/{marathonId}")
    public String listStudents(@PathVariable Long marathonId, Model theModel) {
        logger.info("Get list of all students for marathonId " + marathonId);

        theModel.addAttribute("marathon", marathonService.getMarathonById(marathonId));

        theModel.addAttribute("students",
                userService.getAllByRole(User.Role.TRAINEE.toString()));

        return "students/list-students";
    }

    @GetMapping("/delete/{student_id}")
    public String delete(@PathVariable("student_id") Long id) {
        logger.info("Delete student with id " + id);

        userService.deleteUser(userService.getUserById(id));

        logger.info("Deleted successfully!");

        return "redirect:/students";
    }

    @GetMapping("/remove/{student_id}/{marathon_id}")
    public String removeUserFromMarathon(@PathVariable("student_id") Long userId,
                                         @PathVariable("marathon_id") Long marathonId) {
        logger.info("Remove student #" + userId + " from marathon #" + marathonId);

        User user = userService.getUserById(userId);
        Marathon marathon = marathonService.getMarathonById(marathonId);

        userService.removeUserFromMarathon(user, marathon);

        logger.info("Removed successfully!");

        return "redirect:/students";
    }

    @GetMapping("/add")
    public String showFormForAdd(Model Model) {
        logger.info("Show form for add student");

        User user = new User();

        user.setRole(User.Role.TRAINEE);

        Model.addAttribute("user", user);

        return "students/user-form";
    }

    @GetMapping("/edit/{student_id}")
    public String editStudent(@PathVariable("student_id") Long studentId,
                              Model model) {
        logger.info("Show form to edit student #" + studentId);

        User User = userService.getUserById(studentId);

        model.addAttribute("user", User);

        return "students/user-form";
    }

    @PostMapping("/edit/save/")
    public String editStudent(@ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult) {
        logger.info("Save user to db");

        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getAllErrors().toString());
            return "students/user-form";
        } else {
            userService.createOrUpdateUser(user);
            logger.info("Saved successfully!");
            return "redirect:/students";
        }
    }

    @GetMapping("/view/{studentId}")
    public String viewStudent(@PathVariable Long studentId, Model model) {
        logger.info("View student #" + studentId);

        User User = userService.getUserById(studentId);

        model.addAttribute("user", User);

        return "students/student-view";
    }
}
