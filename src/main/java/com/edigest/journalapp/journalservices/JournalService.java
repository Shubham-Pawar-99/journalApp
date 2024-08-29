package com.edigest.journalapp.journalservices;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edigest.journalapp.Entity.JournalEntry;
import com.edigest.journalapp.Entity.User;
import com.edigest.journalapp.repo.JournalRepo;

@Component
public class JournalService {

    @Autowired
    private JournalRepo repo;

    @Autowired
    private UserService userService;

    // @Transactional
    public void save(JournalEntry entry, String username) {
        try {
            User user = userService.findByusername(username);
            entry.setDate(LocalDate.now());
            JournalEntry saved = repo.save(entry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new RuntimeException("An erroe occured while saving the entry..", e);
        }
    }

    public void save(JournalEntry entry) {
        repo.save(entry);
    }

    public List<JournalEntry> getAll() {
        return repo.findAll();
    }

    public Optional<JournalEntry> findByIdJournal(ObjectId id) {
        return repo.findById(id);
    }

    public boolean deleteById(ObjectId id, String username) {
        boolean removed = false;
        try {
            User user = userService.findByusername(username);
            removed = user.getJournalEntries().removeIf(e -> e.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                repo.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry" + e);
        }
        return removed;
    }

    public List<JournalEntry> findByUsername(String username) {
        return null;
    }
}
