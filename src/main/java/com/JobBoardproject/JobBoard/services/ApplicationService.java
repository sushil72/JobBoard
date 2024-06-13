package com.JobBoardproject.JobBoard.services;

import com.JobBoardproject.JobBoard.model.Application;
import com.JobBoardproject.JobBoard.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    public List<Application> getJobById(Long jobId) {
        return null;
    }

    public List<Application> getApplicationsByCondidateId(Long condidateId) {
        return null;
    }

    public Application getApplicationById(Long id) {
        for (Application application : applicationRepository.findAll()) {
            if (application.getId().equals(id)) {
                return application;
            }
        }
        return null;
    }

    public void deleteApp(Long id) {
        applicationRepository.deleteById(id);
    }
}
