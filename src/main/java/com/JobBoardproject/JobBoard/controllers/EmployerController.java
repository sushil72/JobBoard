package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.config.UserPrinciple;
import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.services.JobService;
import org.apache.catalina.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Employer")
@PreAuthorize("hasRole('ROLE_EMPLOYER')") // This applies to all methods in the controller
public class EmployerController {

    private final JobService jobService;

    public EmployerController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/profile")
    public String EmpDashboard(Model model) {
        return "Employer_Dashboard";
    }

    @GetMapping("/companyprofile")
    public String CompanyProfile(Model model) {
        return "CompanyProfile";
    }

    @GetMapping("/manage-jobs")
    public String ManageJobs(Model model)
    {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userPrinciple.getUsers();
        List<Job> jobs =  jobService.getJobsByEmpId(user.getId());
        System.out.println("Jobs Posted by employee : " + user.getId()+"is :" + jobs);
        model.addAttribute("jobs", jobs);
        return  "Manage-jobs";
    }

    @PostMapping("/update/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute("job") Job jobDetails) {
        jobService.updateJob(id, jobDetails);
        return "redirect:/Employer/manage-jobs";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/Employer/manage-jobs";
    }
}
