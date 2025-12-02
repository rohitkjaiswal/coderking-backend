package com.coderking.service;

import com.coderking.model.Profile;
import com.coderking.model.User;
import com.coderking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
     private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(User u){
        u.setPassword(passwordEncoder.encode(u.getPassword()));


        //Create default profile
        Profile profile=new Profile();
        profile.setFullName(u.getName());
        profile.setBio("New user bio");
        profile.setProfileImage("https://th.bing.com/th/id/OIP.qMCKNkkLIGYq7jjivua5mgHaEp?w=181&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3");

        //Link both sides of the relationship
        profile.setUser(u);
        u.setProfile(profile);
        return userRepository.save(u);  //cascade saves profile too
    }

    public Optional<User> findByEmail(String email){ return userRepository.findByEmail(email); }
}
