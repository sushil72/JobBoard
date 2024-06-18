package com.JobBoardproject.JobBoard.repository;

import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Profile extends JpaRepository<Condidate_Profile, Long> {

}
