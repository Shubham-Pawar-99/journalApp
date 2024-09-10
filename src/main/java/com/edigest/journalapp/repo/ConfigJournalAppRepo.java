package com.edigest.journalapp.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigest.journalapp.Entity.ConfigJournalApp;

public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournalApp, ObjectId> {

}
