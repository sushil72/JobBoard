package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.Exception.UserNotFoundException;
import com.JobBoardproject.JobBoard.config.UserPrinciple;
import com.JobBoardproject.JobBoard.model.Application;
import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.repository.ApplicationRepository;
import com.JobBoardproject.JobBoard.repository.JobRepository;
import com.JobBoardproject.JobBoard.repository.Profile;
import com.JobBoardproject.JobBoard.services.JobService;
import com.JobBoardproject.JobBoard.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/condidate")
public class CondidateController {

    @Autowired
    Profile CondidateRepo;
    @Autowired
    UserService userService;
    @Autowired
    private JobRepository jobRepository;


    @GetMapping("/profile")
    public String profile(Model model) {
        // Get the logged-in user's details
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userPrinciple.getUsers();

        Optional<Condidate_Profile> foundByUsername = CondidateRepo.findById(user.getId());
        foundByUsername.ifPresent(profile -> model.addAttribute("candidate", profile));
        model.addAttribute("User", user);

        // Add user details to the model
//        model.addAttribute("username", user.getUsername());
//        model.addAttribute("userId", user.getId());
//        model.addAttribute("email", user.getEmail());
        return "Condidate_Dashboard";
    }


    @PostMapping("/EditModal/{id}")
    public String UpdateProfile(@PathVariable Long id, @ModelAttribute Condidate_Profile profile, Model model) {
//        System.out.println("updating....");
        Optional<Condidate_Profile> foundProfile = CondidateRepo.findById(id);
        if (foundProfile.isPresent()) {

//            System.out.println("User found with id " +id );

            foundProfile.get().setName(profile.getName());
            foundProfile.get().setEmail(profile.getEmail());
            foundProfile.get().setJobtitle(profile.getJobtitle());
            foundProfile.get().setLocation(profile.getLocation());
            foundProfile.get().setPhone(profile.getPhone());

            CondidateRepo.save(foundProfile.get());

        } else {

            throw new UserNotFoundException("User Not Found");
        }
        return "redirect:/condidate/profile";
    }


    @PostMapping("/uploadImage/{id}")
    public String uploadImage(@PathVariable Long id, @RequestParam("profilePic") MultipartFile file, Model model) throws IOException {
        Optional<Condidate_Profile> foundProfile = CondidateRepo.findById(id);

        if (foundProfile.isPresent()) {
            Condidate_Profile profile = foundProfile.get();
            if (!file.isEmpty()) {
                profile.setProfileImage(file.getBytes());
            }
            CondidateRepo.save(profile);
        } else {
            throw new UserNotFoundException("User Not Found");
        }

        return "redirect:/candidate/profile";
    }

    @GetMapping("/profileImage/{id}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable Long id) {
        Condidate_Profile profile = CondidateRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"profile-pic.jpg\"")
                .body((Resource) new ByteArrayResource(profile.getProfileImage()));
    }


    @PostMapping("/updateOther/{id}")
    public String UpdateOtherInfo(@PathVariable Long id, @ModelAttribute Condidate_Profile profile) {
        Optional<Condidate_Profile> foundprofile = CondidateRepo.findById(id);
        foundprofile.get().setAbout(profile.getAbout());
        foundprofile.get().setSkills(profile.getSkills());
        foundprofile.get().setGithubUrl(profile.getGithubUrl());
        foundprofile.get().setLinkedinUrl(profile.getLinkedinUrl());
        foundprofile.get().setExperience(profile.getExperience());
//        foundprofile.get().setAbout(profile.getAbout());
        CondidateRepo.save(foundprofile.get());
        return "redirect:/condidate/profile";
    }

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/applied_jobs")
    public String viewProfile(Model model) {
        // Retrieve the logged-in user
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userPrinciple.getUsers();
        return "applied-jobs";
    }
}
