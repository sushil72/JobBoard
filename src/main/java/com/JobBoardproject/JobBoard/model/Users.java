package com.JobBoardproject.JobBoard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
//import javax.management.relation.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String password;
        private String email;
        @Enumerated(EnumType.STRING)
        private Role role; // Enum for USER, EMPLOYER, ADMIN
        @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Job> jobs;

        @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Application> applications;
        // Getters and setters
    }

