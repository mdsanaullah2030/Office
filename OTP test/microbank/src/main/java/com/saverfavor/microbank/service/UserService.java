package com.saverfavor.microbank.service;


import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);





    //List All Data show//
    public List<User> getAllUserRegistrations() {
        return userRepository.findAll();
    }


    public User getUserRegistrationById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Registration not found with ID: " + id)
        );
    }






    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user by username: " + username);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        System.out.println("User found: " + user);
        return user;
    }

//Update User/
@Transactional
public void updateUser(long id, User user) throws IOException {
    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with this ID"));

    existingUser.setAddress(user.getAddress());
    existingUser.setPhoneNo(user.getPhoneNo());
    existingUser.setDob(user.getDob());


    userRepository.save(existingUser);
}

}