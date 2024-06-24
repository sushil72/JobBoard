package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.services.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/jobcontrol")
public class JobConroller {
    @Autowired
    private JobService jobService;

    @GetMapping("/jobs")
    public String listJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        jobs.forEach(job -> {
            if (job.getPostedTime() != null) {
                job.setFormattedPostedTime(job.getPostedTime().format(formatter));
            } else {
                job.setFormattedPostedTime("N/A"); // Handle the null case as needed
            }
        });

        model.addAttribute("jobs", jobs);
        return "jobs"; // Ensure this matches your template name
    }


    @PostMapping("/save")
    public String saveJob(@ModelAttribute("job") Job job) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        // Log the Job object before saving
        logger.info("Job object received: {}", job);

        // You can also print the object or its fields
//        System.out.println("Job Title: " + job.getTitle());
//        System.out.println("Job Description: " + job.getDescription());
        // Print other fields as needed
        jobService.createJob(job);
        return "redirect:/jobcontrol/jobs";
    }

    @GetMapping("/edit/{id}")
    public String editJob(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        model.addAttribute("job", job);
        return "job-form";
    }


}
