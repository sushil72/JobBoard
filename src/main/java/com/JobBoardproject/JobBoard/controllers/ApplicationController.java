// src/main/java/com/example/project/controller/ApplicationController.java
package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.model.Application;
import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.repository.ApplicationRepository;
import com.JobBoardproject.JobBoard.repository.JobRepository;
import com.JobBoardproject.JobBoard.repository.UserRepository;
import com.JobBoardproject.JobBoard.services.ApplicationService;
import com.JobBoardproject.JobBoard.services.JobService;
import com.JobBoardproject.JobBoard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository appreopo;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/list")
    public String listApplications(Model model) {
        List<Application> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        return "applications";
    }

    @GetMapping("/job/{job_id}")
    public String listApplicationByJob(@PathVariable Long job_id, Model model)
    {
        List<Application> application = applicationService.getJobById(job_id);
        model.addAttribute("application", application);
        return "applications";
    }

    @GetMapping("/condidate/{condidate_id}")
    public String listApplicationByCondidate(@PathVariable Long condidate_id, Model model)
    {
        List<Application> applications= applicationService.getApplicationsByCondidateId(condidate_id);
        model.addAttribute("applications",applications);
        return "applications";
    }

    @GetMapping("/new")
    public String createApplication(Model model)
    {
        model.addAttribute("application", new Application());
        model.addAttribute("jobs", jobService.getAllJobs());
        model.addAttribute("users", userService.findAllUsers());
        return "application-form";
    }

    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId,
                              @RequestParam("coverLetter") String coverLetter,
                              @RequestParam("resume") MultipartFile resumeFile) throws IOException {
        // Get the current logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<Users> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Users user = optionalUser.get();

        // Get the job
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isEmpty()) {
            throw new RuntimeException("Job not found");
        }
        Job job = optionalJob.get();

        // Create the application
        Application application = new Application();
        application.setCandidate(user);
        application.setJob(job);
        application.setCoverLetter(coverLetter);
        application.setResume(resumeFile.getBytes());
        application.setStatus("Pending");

        // Save the application
        appreopo.save(application);

        return "redirect:/candidate/profile";
    }

    @GetMapping("/edit/{id}")
    public String editApplication(@PathVariable Long id,Model model)
    {
       Application application = applicationService.getApplicationById(id);
       model.addAttribute("application", application);
       return "application-form";
    }

    @PostMapping("/update/{id}")
    public String updateApplication(@PathVariable Long id,@ModelAttribute("application") Application application)
    {

    Application app = applicationService.getApplicationById(id);
        app.setId(application.getId());
        app.setJob(application.getJob());
        app.setStatus(application.getStatus());
        app.setResume(application.getResume());
        applicationService.saveApplication(app);
        return "redirect:/applications";
    }

  @GetMapping("/delete/{id}")
    public String deleteApplication(@PathVariable Long id)
  {
      applicationService.deleteApp(id);
      return "redirect:/application/applications";
  }



}
