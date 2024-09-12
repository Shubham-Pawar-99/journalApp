package com.edigest.journalapp.scheduler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edigest.journalapp.Entity.JournalEntry;
import com.edigest.journalapp.Entity.User;
import com.edigest.journalapp.cache.AppCache;
import com.edigest.journalapp.enums.Sentiment;
import com.edigest.journalapp.journalservices.EmailService;
import com.edigest.journalapp.repo.UserRepoImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private AppCache appCache;

    // @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepoImpl.getUserForSA();
        for (User user : users) {
            List<JournalEntry> list = user.getJournalEntries();
            List<Sentiment> sentiments = list.stream()
                    .filter(x -> x.getDate().isAfter(LocalDate.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            log.info(" List Of Sentements => ", sentiments);

            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(users, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {

                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                emailService.mailSender(user.getEmail(), "Sentiment for Last 7 Days", mostFrequentSentiment.toString());
            }

        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
