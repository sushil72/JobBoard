// src/main/java/com/example/project/controller/ApplicationController.java
package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.model.Application;
import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.services.ApplicationService;
import com.JobBoardproject.JobBoard.services.JobService;
import com.JobBoardproject.JobBoard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listApplications(Model model) {
        List<Application> applications = applicationService.getAllApplications();
        model.addAttribute("applications", applications);
        return "applications"; // Thymeleaf template: src/main/resources/templates/applications.html
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

    @PostMapping("/new")
    public String saveApplication(@ModelAttribute("application") Application application)
    {
        applicationService.saveApplication(application);
        return "redirect:/applications";
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
