package com.JobBoardproject.JobBoard.services;

import com.JobBoardproject.JobBoard.Exception.UserNotFoundException;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public void registerUser(Users user) {
        userRepository.save(user);
    }

    public void loginUser(Users user) {
        Users founduser = userRepository.findByEmail(user.getEmail());
        if(founduser==null || !founduser.getPassword().equals(user.getPassword())) {
            throw new UserNotFoundException("Invalid username or password");
        }

    }


    public Object findAllUsers() {
        return userRepository.findAll();
    }
}

