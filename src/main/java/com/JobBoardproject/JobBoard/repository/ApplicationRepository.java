package com.JobBoardproject.JobBoard.repository;

import com.JobBoardproject.JobBoard.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
