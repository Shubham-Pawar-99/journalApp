package com.edigest.journalapp.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigest.journalapp.Entity.JournalEntry;

public interface JournalRepo extends MongoRepository<JournalEntry, ObjectId> {

}
