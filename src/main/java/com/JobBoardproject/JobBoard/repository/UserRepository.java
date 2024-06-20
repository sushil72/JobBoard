package com.JobBoardproject.JobBoard.repository;

import com.JobBoardproject.JobBoard.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    Optional<Users> findByUsername(String username);
}
