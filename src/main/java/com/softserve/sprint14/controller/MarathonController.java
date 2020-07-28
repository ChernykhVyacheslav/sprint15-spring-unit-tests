package com.softserve.sprint14.controller;


import com.softserve.sprint14.dto.MarathonDTO;
import com.softserve.sprint14.entity.Marathon;
import com.softserve.sprint14.service.MarathonService;
import com.softserve.sprint14.service.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/marathons")
public class MarathonController {

    private MarathonService marathonService;

    private UserService userService;

    private ModelMapper modelMapper;

    private Converter<MarathonDTO, Marathon> handleEnteredMarathon = new Converter<MarathonDTO, Marathon>() {
        @Override
        public Marathon convert(MappingContext<MarathonDTO, Marathon> context) {
            Marathon marathon = new Marathon();
            if(context.getSource().getId() == null) {
                marathon.setUsers(new ArrayList<>());
            } else {
                marathon.setId(context.getSource().getId());
            }
            marathon.setClosed(context.getSource().isClosed());
            marathon.setTitle(context.getSource().getTitle());
            for (Long userId : context.getSource().getUsers()) {
                marathon.getUsers()
                        .add(userService.getUserById(userId));
            }
            marathon.setSprints(context.getSource().getSprints());

            return marathon;
        }
    };


    @Autowired
    public MarathonController(MarathonService marathonService, UserService userService, ModelMapper modelMapper) {
        this.marathonService = marathonService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.modelMapper.addConverter(handleEnteredMarathon);
    }

    @GetMapping
    public String listMarathons(Model theModel) {
        List<Marathon> theMarathons = marathonService.getAll();

        theModel.addAttribute("marathons", theMarathons);

        return "marathons/list-marathons";
    }

    @GetMapping("/create")
    public String showFormForAdd(Model theModel) {
        Marathon theMarathon = new Marathon();

        theModel.addAttribute("marathon", theMarathon);

        return "marathons/marathon-form";
    }

    @GetMapping("/update")
    public String showFormForUpdate(@RequestParam("marathonId") Long theId,
                                    Model theModel) {

        Marathon theMarathon = marathonService.getMarathonById(theId);

        theModel.addAttribute("marathon", theMarathon);

        return "marathons/marathon-form";
    }

    @PostMapping("/save")
    public String saveMarathon(
            @ModelAttribute("marathon") @Valid Marathon theMarathon,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "marathons/marathon-form";
        } else {
            marathonService.createOrUpdateMarathon(theMarathon);
            return "redirect:/marathons";
        }
    }

    @GetMapping("/close")
    public String close(@RequestParam("marathonId") Long theId) {

        marathonService.closeMarathonById(theId);

        return "redirect:/marathons";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("marathonId") Long theId) {

        marathonService.deleteMarathonById(theId);

        return "redirect:/marathons";

    }

}
