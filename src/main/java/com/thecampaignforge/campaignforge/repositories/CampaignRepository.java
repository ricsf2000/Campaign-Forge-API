package com.thecampaignforge.campaignforge.repositories;

import com.thecampaignforge.campaignforge.models.Campaign;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends MongoRepository<Campaign, ObjectId> {

    List<Campaign> findByUserId(String userId);

    Optional<Campaign> findByIdAndUserId(ObjectId id, String userId);
}
