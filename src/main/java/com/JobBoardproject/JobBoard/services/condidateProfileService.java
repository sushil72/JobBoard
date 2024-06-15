package com.JobBoardproject.JobBoard.services;

import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import com.JobBoardproject.JobBoard.repository.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class condidateProfileService {
    @Autowired
    Profile profileRepo;
    public Optional<Condidate_Profile> findCondidateProfileById(Long id) {
        Optional<Condidate_Profile> condidateProfile = profileRepo.findById(id);
        return condidateProfile;
    }

    public void saveProfile(Condidate_Profile profile) {
        profileRepo.save(profile);
    }

    public void UpdateProfile(Long id, Condidate_Profile profile) {
        Condidate_Profile condidateProfile=profileRepo.findById(id).get();
        condidateProfile.setName(profile.getName());
        condidateProfile.setEmail(profile.getEmail());
        condidateProfile.setLocation(profile.getLocation());
        condidateProfile.setAbout(profile.getAbout());
        condidateProfile.setExperience(profile.getExperience());
        condidateProfile.setPhone(profile.getPhone());
        condidateProfile.setJobtitle(profile.getJobtitle());
        condidateProfile.setGithubUrl(profile.getGithubUrl());
        condidateProfile.setLinkedinUrl(profile.getLinkedinUrl());
        condidateProfile.setSkills(profile.getSkills());
        profileRepo.save(condidateProfile);
    }

    public void EditProfileModal(Long id, Condidate_Profile profile) {
        Condidate_Profile condidateProfile=profileRepo.findById(id).get();
        condidateProfile.setName(profile.getName());
        condidateProfile.setEmail(profile.getEmail());
        condidateProfile.setLocation(profile.getLocation());
        condidateProfile.setJobtitle(profile.getJobtitle());
        condidateProfile.setPhone(profile.getPhone());
        profileRepo.save(condidateProfile);
    }
}
