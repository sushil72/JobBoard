package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.Exception.ResourceNotFoundException;
import com.JobBoardproject.JobBoard.Exception.UserNotFoundException;
import com.JobBoardproject.JobBoard.config.UserPrinciple;
import com.JobBoardproject.JobBoard.model.Application;
import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.repository.ApplicationRepository;
import com.JobBoardproject.JobBoard.repository.JobRepository;
import com.JobBoardproject.JobBoard.repository.Profile;
import com.JobBoardproject.JobBoard.services.ApplicationService;
import com.JobBoardproject.JobBoard.services.JobService;
import com.JobBoardproject.JobBoard.services.UserService;
import jakarta.annotation.Resource;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/candidate")

public class CondidateController {

    @Autowired
    Profile CondidateRepo;
    @Autowired
    UserService userService;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobService jobService;



    @GetMapping("/profile")
    public String profile(Model model) {
        // Get the logged-in user's details
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userPrinciple.getUsers();

        Optional<Condidate_Profile> foundByUsername = CondidateRepo.findById(user.getId());
        if (foundByUsername.isPresent()) {
            model.addAttribute("candidate", foundByUsername.get());
        }
        else {
            throw  new UserNotFoundException("User not found");
        }
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
        return "redirect:/candidate/profile";
    }




    @Value("${user.dir}")
    private String userDir;

    @PostMapping("/uploadImage/{id}")
    public String uploadImage(@PathVariable Long id, @RequestParam("profilePic") MultipartFile file) throws IOException {
        Optional<Condidate_Profile> profile = CondidateRepo.findById(id);
        if (profile.isPresent()) {
            profile.get().setProfileImage(file.getOriginalFilename());
            Condidate_Profile uploaded = CondidateRepo.save(profile.get());
            if (uploaded != null) {
                try {
                    String uploadDir = userDir + "/src/main/resources/static/Profile_Pictures";
                    Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
                    System.out.println("Path " + path);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "redirect:/candidate/profile";
    }


//


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
        return "redirect:/candidate/profile";
    }




    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    ApplicationService appService;

    @GetMapping("/applied_jobs")

    public String viewProfile(Model model) {

        // Retrieve the logged-in user
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userPrinciple.getUsers();
        Application application = applicationRepository.findById(user.getId()).get();
        System.out.println("Logged in user is :"+user.getId());
//        System.out.println("Application is :"+application.getJob());

        List<Job> jobs =appService.getJobsByCandidateId(user.getId());
        System.out.println("All job present in the application");
        System.out.println(jobs);
        model.addAttribute("jobs", jobs);
        model.addAttribute("application", application);
        return "applied-jobs";
    }


}
