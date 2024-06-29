package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.config.UserPrinciple;
import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.repository.JobRepository;
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
    private final JobRepository jobRepository;


    public EmployerController(JobService jobService, JobRepository jobRepository) {
        this.jobService = jobService;
        this.jobRepository = jobRepository;
    }

    @GetMapping("/profile")
    public String EmpDashboard(Model model) {

        model.addAttribute("job", new Job());
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
        System.out.println("number of jobs found ="+jobs.size());
//        System.out.println("Jobs Posted by employee : " + user.getId()+"is :" + jobs);
        model.addAttribute("jobs", jobs);
        return  "Manage-jobs";
    }

    @GetMapping("edit-job/{id}")
    public String EditJob(Model model, @PathVariable Long id) {
        System.out.println("job id : " + id);
        Job jobbyid = jobRepository.findByID(id);
        model.addAttribute("job", jobbyid);
        return  "edit-form";
    }

    @PostMapping("/update-job/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute("job") Job jobDetails) {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userPrinciple.getUsers();
        jobDetails.setEmployer(user);
        jobService.updateJob(id, jobDetails);
        return "redirect:/Employer/manage-jobs";
    }

    @GetMapping("/delete-job/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/Employer/manage-jobs";
    }
    @GetMapping("/view/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        return "redirect:/job/" + id;
    }
}
