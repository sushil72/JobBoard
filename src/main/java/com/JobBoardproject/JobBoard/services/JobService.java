package com.JobBoardproject.JobBoard.services;

import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }



    public void createJob(Job job) {
        jobRepository.save(job);
    }

    public Job getJobById(Long id) {
        Job job = jobRepository.findByID(id);

        return job;
    }


    public void updateJob(Long id, Job jobDetails) {
        Job job = getJobById(id);
        job.setDescription(jobDetails.getDescription());
        job.setTitle(jobDetails.getTitle());
        job.setLocation(jobDetails.getLocation());
        job.setCompanyName(jobDetails.getCompanyName());
        jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        Job job = getJobById(id);
        jobRepository.delete(job);
    }

    public List<Job> getJobsByEmpId(Long id) {
        return jobRepository.findbyEmpID(id);
    }
}
