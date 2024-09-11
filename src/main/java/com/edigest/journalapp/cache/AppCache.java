package com.edigest.journalapp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edigest.journalapp.Entity.ConfigJournalApp;
import com.edigest.journalapp.repo.ConfigJournalAppRepo;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    public Map<String, String> appCache= new HashMap<>();

    @PostConstruct
    public void init() { 
        List<ConfigJournalApp> all = configJournalAppRepo.findAll();
        if (all != null && !all.isEmpty()) {
            for (ConfigJournalApp configJournalApp : all) {
                appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
            }
        } else {
            // Optionally, handle empty or missing data here
            System.out.println("No configurations found in the database.");
        }
    }
}
