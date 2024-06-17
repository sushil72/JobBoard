package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.config.UserPrinciple;
import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.services.UserService;
import com.JobBoardproject.JobBoard.services.condidateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/candidate")
public class CondidateController {

    @Autowired
    condidateProfileService condidateService;
    @Autowired
    UserService userService;


    @GetMapping("/profile")
    public String profile(Model model) {
        // Get the logged-in user's details
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userPrinciple.getUsers();

        // Add user details to the model
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userId", user.getId());
        model.addAttribute("email", user.getEmail());
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
