package com.edigest.journalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalapp.Entity.User;
import com.edigest.journalapp.journalservices.UserDetailServiceImpl;
import com.edigest.journalapp.journalservices.UserService;
import com.edigest.journalapp.utilis.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheckString() {
        return "Ok";
    }

    // Create User and assign role as USER
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User entity) {
        boolean result = userService.saveNewUser(entity);
        if (result) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("User Login Failed!", e);
            return new ResponseEntity<>("Incorrect Username And Password",HttpStatus.BAD_REQUEST);
        }

    }

}
