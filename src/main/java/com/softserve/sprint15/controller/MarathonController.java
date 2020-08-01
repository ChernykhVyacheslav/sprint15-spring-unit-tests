package com.softserve.sprint15.controller;


import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.entity.User;
import com.softserve.sprint15.service.MarathonService;
import com.softserve.sprint15.service.UserService;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/marathons")
@AllArgsConstructor
public class MarathonController {

    private Logger logger = LoggerFactory.getLogger(MarathonController.class);

    private MarathonService marathonService;

    private UserService userService;

    @Autowired
    public MarathonController(MarathonService marathonService, UserService userService) {
        logger.info("Initializing MarathonController");
        this.marathonService = marathonService;
        this.userService = userService;
    }

    @GetMapping
    public String listMarathons(Model model) {

        logger.info("Get list of all Marathons");

        List<Marathon> Marathons = marathonService.getAll();

        model.addAttribute("marathons", Marathons);

        model.addAttribute("showClosed", false);

        return "marathons/list-marathons";
    }

    @GetMapping("/{userId}")
    public String listMarathons(@PathVariable Long userId, Model theModel) {

        logger.info("Get list of all Marathons for userId " + userId);

        User user = userService.getUserById(userId);

        theModel.addAttribute("showClosed", false);

        theModel.addAttribute("user", user);

        theModel.addAttribute("marathons",
                marathonService.getAll());

        return "marathons/list-marathons";
    }

    @GetMapping("/add")
    public String showFormForAdd(Model model) {

        logger.info("Show form for add");
        Marathon Marathon = new Marathon();

        model.addAttribute("marathon", Marathon);

        return "marathons/marathon-form";
    }

    @GetMapping("/edit/{marathonId}")
    public String showFormForUpdate(@PathVariable("marathonId") Long id,
                                    Model model) {
        logger.info("Show form for update marathons  #" + id);

        Marathon Marathon = marathonService.getMarathonById(id);

        model.addAttribute("marathon", Marathon);

        return "marathons/marathon-form";
    }

    @PostMapping("/save")
    public String saveMarathon(
            @ModelAttribute("marathon") @Valid Marathon marathon,
            BindingResult bindingResult) {
        logger.info("Save marathons");
        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.getAllErrors().toString());
            return "marathons/marathon-form";
        } else {
            marathonService.createOrUpdateMarathon(marathon);
            logger.info("Saved successfully!");
            return "redirect:/marathons";
        }
    }

    @GetMapping("/close/{marathonId}")
    public String close(@PathVariable("marathonId") Long id) {

        logger.info("Close marathon with id #" + id);

        marathonService.closeMarathonById(id);

        return "redirect:/marathons";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {

        logger.info("Delete marathons with id #" + id);

        marathonService.deleteMarathonById(id);

        return "redirect:/marathons";

    }

    @GetMapping("/view/{marathonId}")
    public String viewMarathon(@PathVariable Long marathonId, Model model) {

        logger.info("View marathon with marathonId #" + marathonId);

        Marathon Marathon = marathonService.getMarathonById(marathonId);

        model.addAttribute("marathon", Marathon);

        return "marathons/marathon-view";
    }

    @GetMapping("/closed")
    public String listClosedMarathons(Model theModel) {

        logger.info("Close list of Marathons");

        theModel.addAttribute("showClosed", true);

        theModel.addAttribute("marathons",
                marathonService.getAll());

        return "marathons/list-marathons";
    }

    @GetMapping("/open/{marathonId}")
    public String open(@PathVariable Long marathonId) {

        logger.info("Open Marathon with marathonId #" + marathonId);

        marathonService.openMarathonById(marathonId);

        return "redirect:/marathons";
    }
}
