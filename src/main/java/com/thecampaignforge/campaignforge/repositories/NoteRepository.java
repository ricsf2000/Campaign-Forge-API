package com.thecampaignforge.campaignforge.repositories;

import com.thecampaignforge.campaignforge.models.Note;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, ObjectId> {
    List<Note> findByCampaignId(ObjectId campaignId);
}
