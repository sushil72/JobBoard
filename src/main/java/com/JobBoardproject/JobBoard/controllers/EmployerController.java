package com.JobBoardproject.JobBoard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Employer")
public class EmployerController {

    @GetMapping("/profile")
    public String EmpDashboard(Model model) {
        return "Employer_Dashboard";
    }
}
