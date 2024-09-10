package com.edigest.journalapp.repositorytest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edigest.journalapp.repo.UserRepoImpl;
import com.mongodb.assertions.Assertions;

@SpringBootTest
public class UserrRepoImplTest {
    
    @Autowired
    private UserRepoImpl userRepoImpl;

    @Test
    public void testSaveNewUser(){
        Assertions.assertNotNull(userRepoImpl.getUserForSA());
        // userRepoImpl.getUserForSA();
    }
}
