package com.JobBoardproject.JobBoard.controllers;

import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService service;
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Assuming "register.html" is the name of your register page template
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users user) {
        service.registerUser(user);
        return "redirect:/auth/login";
    }


    @RequestMapping("/login")
    public String LoginUser()
    {

        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute Users user, Model model)
    {
            service.loginUser(user);
            return "redirect:/auth/index";
    }
}
