package com.softserve.sprint14.controller;


import com.softserve.sprint14.entity.Marathon;
import com.softserve.sprint14.service.MarathonService;
import lombok.AllArgsConstructor;
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

    private MarathonService marathonService;

    @GetMapping
    public String listMarathons(Model Model) {
        List<Marathon> Marathons = marathonService.getAll();

        Model.addAttribute("marathons", Marathons);

        return "marathons/list-marathons";
    }

    @GetMapping("/create")
    public String showFormForAdd(Model Model) {
        Marathon Marathon = new Marathon();

        Model.addAttribute("marathon", Marathon);

        return "marathons/marathon-form";
    }

    @GetMapping("/edit/{marathonId}")
    public String showFormForUpdate(@PathVariable("marathonId") Long id,
                                    Model Model) {

        Marathon Marathon = marathonService.getMarathonById(id);

        Model.addAttribute("marathon", Marathon);

        return "marathons/marathon-form";
    }

    @PostMapping("/save")
    public String saveMarathon(
            @ModelAttribute("marathon") @Valid Marathon Marathon,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "marathons/marathon-form";
        } else {
            marathonService.createOrUpdateMarathon(Marathon);
            return "redirect:/marathons";
        }
    }

    @GetMapping("/close/{marathonId}")
    public String close(@PathVariable("marathonId") Long id) {

        marathonService.closeMarathonById(id);

        return "redirect:/marathons";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {

        marathonService.deleteMarathonById(id);

        return "redirect:/marathons";

    }

}
