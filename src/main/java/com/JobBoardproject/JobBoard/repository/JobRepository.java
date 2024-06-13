package com.JobBoardproject.JobBoard.repository;

import com.JobBoardproject.JobBoard.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j WHERE j.id = :jobId")
Job findByID(Long jobId);
}
