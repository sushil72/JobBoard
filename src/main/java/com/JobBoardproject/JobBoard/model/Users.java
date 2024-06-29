package com.JobBoardproject.JobBoard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.Set;
//import javax.management.relation.Role;


@Entity
@Table(name = "users")
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String password;
        private String email;

        private String role; // Enum for USER, EMPLOYER, ADMIN
        @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Job> jobs;

        @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Application> applications;
        // Getters and setters

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getRole() {
                return role;
        }

        public void setRole(String role) {
                this.role = role;
        }

        public Set<Job> getJobs() {
                return jobs;
        }

        public void setJobs(Set<Job> jobs) {
                this.jobs = jobs;
        }

        public Set<Application> getApplications() {
                return applications;
        }

        public void setApplications(Set<Application> applications) {
                this.applications = applications;
        }
}

