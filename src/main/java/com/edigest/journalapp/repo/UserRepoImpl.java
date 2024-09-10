package com.edigest.journalapp.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.edigest.journalapp.Entity.User;

@Service
public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {
        Query query = new Query();
        // query.addCriteria(Criteria.where("username").is("ram"));
        // query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", "i"));
        // query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        query.addCriteria(Criteria.where("roles").is("ADMIN"));
        List<User> list = mongoTemplate.find(query, User.class);
        return list;
    }
}
