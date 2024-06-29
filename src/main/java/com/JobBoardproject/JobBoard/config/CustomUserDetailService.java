package com.JobBoardproject.JobBoard.config;

import com.JobBoardproject.JobBoard.model.Users;
import com.JobBoardproject.JobBoard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        UserPrinciple userPrinciple = new UserPrinciple();
        userPrinciple.setUser(user);
        return userPrinciple;
//        return new UserPrinciple(user);
    }
}
