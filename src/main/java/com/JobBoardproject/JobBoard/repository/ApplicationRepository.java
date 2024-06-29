package com.JobBoardproject.JobBoard.repository;

import com.JobBoardproject.JobBoard.model.Application;
import com.JobBoardproject.JobBoard.model.Job;
import com.JobBoardproject.JobBoard.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCandidateId(Long id);
    @Query("SELECT a.job FROM Application a WHERE a.candidate.id = :id")
    List<Job> findAllByCandidateId(@Param("id") Long id);
}
