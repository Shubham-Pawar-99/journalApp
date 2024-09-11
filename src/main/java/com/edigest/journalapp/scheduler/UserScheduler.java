package com.edigest.journalapp.scheduler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edigest.journalapp.Entity.JournalEntry;
import com.edigest.journalapp.Entity.User;
import com.edigest.journalapp.cache.AppCache;
import com.edigest.journalapp.journalservices.EmailService;
import com.edigest.journalapp.journalservices.SentimentAnalysisService;
import com.edigest.journalapp.repo.UserRepoImpl;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepoImpl.getUserForSA();
        for (User user : users) {
            List<JournalEntry> list = user.getJournalEntries();
            List<String> filteredEntries = list.stream()
                    .filter(x -> x.getDate().isAfter(LocalDate.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getContent())
                    .collect(Collectors.toList());

            String join = String.join(" ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(join);
            // emailService.mailSender(user.getEmail(), "Sentiment for last 7 days",
            // sentiment);

        }
    }
@Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
