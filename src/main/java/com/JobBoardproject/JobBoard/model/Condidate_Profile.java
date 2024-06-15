package com.JobBoardproject.JobBoard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Condidate_Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String jobtitle;
    private String location;
    @Column(columnDefinition = "TEXT")
    private String about;
    private String email;
    private String phone;
    @ElementCollection
    private List<String> skills;
    @Lob
    private Blob Profieimage;
    private String linkedinUrl;
    private String githubUrl;
    private String experience;

}
