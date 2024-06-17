package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.Exception.UserNotFoundException;
import com.JobBoardproject.JobBoard.config.UserPrinciple;
import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.repository.Profile;
import com.JobBoardproject.JobBoard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/condidate")
public class CondidateController {

    @Autowired
    Profile CondidateRepo;
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


    @PostMapping("/EditModal/{id}")
    public String UpdateProfile(@PathVariable Long id,@ModelAttribute Condidate_Profile profile,Model model )
    {
//        System.out.println("updating....");
        Optional<Condidate_Profile> foundProfile=CondidateRepo.findById(id);
        if(foundProfile.isPresent())
        {
            Condidate_Profile existingProfile=foundProfile.get();
//            System.out.println("User found with id " +id );

            foundProfile.get().setName(profile.getName());
            foundProfile.get().setEmail(profile.getEmail());
            foundProfile.get().setJobtitle(profile.getJobtitle());
            foundProfile.get().setLocation(profile.getLocation());
            foundProfile.get().setPhone(profile.getPhone());

            CondidateRepo.save(existingProfile);
            System.out.println(existingProfile);
                model.addAttribute("updatedjobtitle",existingProfile.getJobtitle());
                model.addAttribute("updatedlocation",existingProfile.getLocation());
                model.addAttribute("updatedphone",existingProfile.getPhone());
                model.addAttribute("udatedemail",existingProfile.getEmail());
                model.addAttribute("updatedname",existingProfile.getName());
//                model.addAttribute("profile", foundProfile.get());
        }
        else {

            throw new UserNotFoundException("User Not Found");
        }
        return "redirect:/condidate/profile";
    }
}
