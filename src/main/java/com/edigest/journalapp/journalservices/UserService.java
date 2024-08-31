package com.edigest.journalapp.journalservices;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edigest.journalapp.Entity.User;
import com.edigest.journalapp.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User entry) {

        try {
            entry.setPassword(passwordEncoder.encode(entry.getPassword()));
            entry.setRoles(Arrays.asList("USER"));
            repo.save(entry);
            return true;
            
        } catch (Exception e) {
            logger.error("errrrrro");
            return false;
        }
    }

    public void saveAdmin(User entry) {
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        entry.setRoles(Arrays.asList("USER", "ADMIN"));
        repo.save(entry);
    }

    public void saveUser(User entry) {
        repo.save(entry);
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return repo.findById(id);
    }

    public void deleteById(ObjectId id) {
        repo.deleteById(id);
    }

    public User findByusername(String username) {
        return repo.findByUsername(username);
    }

}
