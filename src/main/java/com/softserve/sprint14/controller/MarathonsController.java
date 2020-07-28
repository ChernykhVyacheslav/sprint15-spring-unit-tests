package com.softserve.sprint14.controller;


import com.softserve.sprint14.entity.Marathon;
import com.softserve.sprint14.service.MarathonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/marathons")
public class MarathonsController {



    private MarathonService marathonService;

    public MarathonsController(MarathonService theMarathonService) {
        marathonService = theMarathonService;
    }

    // add mapping for "/list"

    @GetMapping("/list")
    public String listMarathons(Model theModel) {

        // get marathons from db
        List<Marathon> theMarathons = marathonService.getAll();

        // add to the spring model
        theModel.addAttribute("marathons", theMarathons);

        return "marathons/list-marathons";
    }

//    @GetMapping("/showFormForAdd")
//    public String showFormForAdd(Model theModel) {
//
//        // create model attribute to bind form data
//        Marathon theMarathon = new Marathon();
//
//        theModel.addAttribute("marathon", theMarathon);
//
//        return "marathons/marathon-form";
//    }
//
//    @GetMapping("/showFormForUpdate")
//    public String showFormForUpdate(@RequestParam("marathonId") int theId,
//                                    Model theModel) {
//
//        // get the marathon from the service
//        Marathon theMarathon = marathonService.findById(theId);
//
//        // set marathon as a model attribute to pre-populate the form
//        theModel.addAttribute("marathon", theMarathon);
//
//        // send over to our form
//        return "marathons/marathon-form";
//    }
//
//
//    @PostMapping("/save")
//    public String saveMarathon(
//            @ModelAttribute("marathon") @Valid Marathon theMarathon,
//            BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return "marathons/marathon-form";
//        }
//        else {
//            // save the marathon
//            marathonService.save(theMarathon);
//
//            // use a redirect to prevent duplicate submissions
//            return "redirect:/marathons/list";
//        }
//    }
//
//
//    @GetMapping("/delete")
//    public String delete(@RequestParam("marathonId") int theId) {
//
//        // delete the marathon
//        marathonService.deleteById(theId);
//
//        // redirect to /marathons/list
//        return "redirect:/marathons/list";
//
//    }
//
//
//    @GetMapping("/search")
//    public String search(@RequestParam("firstName") String theFirstName,
//                         @RequestParam("lastName") String theLastName,
//                         Model theModel) {
//
//        // check names, if both are empty then just give list of all marathons
//
//        if (theFirstName.trim().isEmpty() && theLastName.trim().isEmpty()) {
//            return "redirect:/marathons/list";
//        }
//        else {
//            // else, search by first name and last name
//            List<Marathon> theMarathons =
//                    marathonService.searchBy(theFirstName, theLastName);
//
//            // add to the spring model
//            theModel.addAttribute("marathons", theMarathons);
//
//            // send to list-marathons
//            return "marathons/list-marathons";
//        }
//
//    }
}
