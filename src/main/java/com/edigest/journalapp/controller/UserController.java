package com.edigest.journalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalapp.Entity.User;
import com.edigest.journalapp.api.response.WeatherResponse;
import com.edigest.journalapp.journalservices.EmailService;
import com.edigest.journalapp.journalservices.UserService;
import com.edigest.journalapp.journalservices.WeatherService;
import com.edigest.journalapp.repo.UserRepo;
import com.edigest.journalapp.repo.UserRepoImpl;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private EmailService emailService;

    // UPDATE USER
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User dbuser = userService.findByusername(username);

        if (dbuser != null) {
            dbuser.setUsername(user.getUsername());
            dbuser.setPassword(user.getPassword());
            userService.saveNewUser(dbuser);
            return new ResponseEntity<>(dbuser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete User
    @DeleteMapping
    public ResponseEntity<?> deleteByUserName(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // for rest templete => to handle the external api requests
    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Pune");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like a " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }

    // Mongo Templete
    @GetMapping("/userRepoImplTest")
    public List<User> getMethodName() {
        List<User> users = userRepoImpl.getUserForSA();
        return users;
    }

    // mail sender
    @PostMapping("/send-mail")
    public String testSendMail() {
        emailService.mailSender("momelim549@janfab.com",
                "Testing Mail Sender 2.0",
                "Hi, How are you ? How was your day gone! ");
        return "Success";
    }

}
