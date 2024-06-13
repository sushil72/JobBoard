package com.JobBoardproject.JobBoard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String companyName;
    private String location;
    private LocalDateTime postedTime;
    private String salary;
    //changes
    public List<String> jobTypes;
    public List<String>Skills;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String KeyFeatures;
    private LocalDateTime ExpiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Users employer;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Application> applications;

    @Transient
    private String formattedPostedTime;

    public String getFormattedPostedTime() {
        return formattedPostedTime;
    }

    public void setFormattedPostedTime(String formattedPostedTime) {
        this.formattedPostedTime = formattedPostedTime;
    }

}
