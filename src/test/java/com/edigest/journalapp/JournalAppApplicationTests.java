package com.edigest.journalapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edigest.journalapp.scheduler.UserScheduler;

@SpringBootTest
class JournalAppApplicationTests {

	@Autowired
	private UserScheduler scheduler;

	@Test
	void contextLoads() {
	}

	// User Schedular Test
	@Test
	void userShedulerTest() {
		scheduler.fetchUsersAndSendSaMail();
	}
}
