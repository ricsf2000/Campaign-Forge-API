package com.thecampaignforge.campaignforge.repositories;

import com.thecampaignforge.campaignforge.models.Map;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends MongoRepository<Map, ObjectId> {
    List<Map> findByCampaignId(String campaignId);
}
