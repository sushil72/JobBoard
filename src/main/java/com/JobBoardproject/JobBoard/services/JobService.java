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
        Job job1 = new Job();
        job1.setTitle(job.getTitle());
        job1.setDescription(job.getDescription());
        job1.setEmployer(job.getEmployer());
        job1.setLocation(job.getLocation());
//        job1.setApplications(job.getApplications());
        job1.setJobTypes(job.getJobTypes());
        job1.setPostedTime(job.getPostedTime());
        job1.setCompanyName(job.getCompanyName());
        job1.setExpiryDate(job.getExpiryDate());
        job1.setSalary(job.getSalary());
        job1.setId(job.getId());
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
        job.setExpiryDate(jobDetails.getExpiryDate());
        job.setSalary(jobDetails.getSalary());
        job.setJobTypes(jobDetails.getJobTypes());
        job.setPostedTime(jobDetails.getPostedTime());
        job.setEmployer(jobDetails.getEmployer());
        job.setSkills(jobDetails.getSkills());
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
