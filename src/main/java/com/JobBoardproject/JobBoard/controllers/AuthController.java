package com.JobBoardproject.JobBoard.controllers;


import com.JobBoardproject.JobBoard.model.Condidate_Profile;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.repository.Profile;
import com.JobBoardproject.JobBoard.repository.UserRepository;
import com.JobBoardproject.JobBoard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
    private Profile CondidateRepo;

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String role, Model model) {
        if (userRepository.findByEmail(email)!= null) {
            model.addAttribute("error", "User already exists");
            return "register";
        }
        Users user = new Users();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setEmail(email);

        userRepository.save(user);
        Condidate_Profile profile = new Condidate_Profile();
        profile.setEmail(email);
        profile.setId(user.getId());
        profile.setName(username);
        CondidateRepo.save(profile);
        System.out.println("Registerd successfully");
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @RequestMapping("/login/success")
    public String loginSuccess() {
        return "loginSuccess";
    }
}

