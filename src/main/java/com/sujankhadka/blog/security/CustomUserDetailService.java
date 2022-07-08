package com.sujankhadka.blog.security;

import com.sujankhadka.blog.entities.User;
import com.sujankhadka.blog.exceptions.ResourceNotFoundException;
import com.sujankhadka.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from database by username
        User userDetails=this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "Email: "+username, 0));
        System.out.println(userDetails);
        return userDetails;
    }
}
