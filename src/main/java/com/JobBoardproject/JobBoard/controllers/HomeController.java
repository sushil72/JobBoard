package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.services.JobService;
import com.JobBoardproject.JobBoard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.JobBoardproject.JobBoard.model.Condidate_Profile;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    JobService jobService;
    UserService userService;

    @GetMapping({"home","/"})
    public String Home(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));

        model.addAttribute("isLoggedIn", isLoggedIn);

        List<Job> jobs = jobService.getAllJobs();
        if (jobs.isEmpty()) {
            model.addAttribute("message", "No Job Found");
        } else {
            model.addAttribute("jobs", jobs);
        }
        return "index";
    }

    @GetMapping("/job/{id}")
    public String jobDetails(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        if (job == null) {
//            System.out.println("job not found");
            model.addAttribute("message", "Job not found");
        } else {
            model.addAttribute("job", job);
        }
        return "single-job";
    }
    @GetMapping("/condidate")
    public String condidate(Model model)
    {

        return "Condidate_Profile";
    }
}
