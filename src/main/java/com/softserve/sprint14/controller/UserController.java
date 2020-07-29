package com.softserve.sprint14.controller;

import com.softserve.sprint14.dto.MarathonDTO;
import com.softserve.sprint14.entity.Marathon;
import com.softserve.sprint14.entity.User;
import com.softserve.sprint14.service.MarathonService;
import com.softserve.sprint14.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class UserController {

    private MarathonService marathonService;

    private UserService userService;

    @Autowired
    public UserController(MarathonService marathonService, UserService userService) {
        this.marathonService = marathonService;
        this.userService = userService;
    }

    @GetMapping("/{marathonId}/add")
    public String addUsersForm(Model theModel, @PathVariable Long marathonId) {

        Marathon theMarathon = marathonService.getMarathonById(marathonId);

        theModel.addAttribute("marathon", theMarathon);

        theModel.addAttribute("users", userService.getAllByRole(User.Role.TRAINEE.toString()));

        return "students/add-students-form";
    }

    @PostMapping("/{marathonId}/add-students")
    public String addUsersToMarathon(
            @ModelAttribute("marathon") MarathonDTO marathon, @PathVariable Long marathonId,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "students/add-students-form";
        } else {
            Marathon tempMarathon = marathonService.getMarathonById(marathonId);
            for (Long userId:
                 marathon.getUsers()) {
                System.out.println(userId);
                userService.addUserToMarathon(userService.getUserById(userId), tempMarathon);
            }
            return "redirect:/students";
        }
    }
}
