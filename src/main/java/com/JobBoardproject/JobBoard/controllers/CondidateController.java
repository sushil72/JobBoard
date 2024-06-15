package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import com.JobBoardproject.JobBoard.services.UserService;
import com.JobBoardproject.JobBoard.services.condidateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/condidate")
public class CondidateController {
    @Autowired
    condidateProfileService condidateService;
    @Autowired
    UserService userService;
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, Model model)
    {
        Optional<Condidate_Profile> profile =condidateService.findCondidateProfileById(id);

        model.addAttribute("profile", profile);
        return "Condidate_Dashboard";
    }

    @PostMapping("/save")
    public String SaveProfile(@ModelAttribute("profile") Condidate_Profile profile)
    {
        condidateService.saveProfile(profile);
        return "redirect:/profile/{id}";
    }
    @PostMapping("/EditModal/{id}")
    public String UpdateProfile(@PathVariable Long id, @ModelAttribute("profile") Condidate_Profile profile)
    {
        System.out.println("updating....");
        condidateService.EditProfileModal(id,profile);
        return "redirect:/profile/{id}";
    }
}
