package com.thecampaignforge.campaignforge.services;

import com.thecampaignforge.campaignforge.repositories.CampaignRepository;
import com.thecampaignforge.campaignforge.models.Campaign;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public List<Campaign> getCampaignsByUserId(String userId) {
        return campaignRepository.findByUserId(userId);
    }

    public Optional<Campaign> getCampaignByIdAndUserId(ObjectId id, String userId) {
        return campaignRepository.findByIdAndUserId(id, userId);
    }

    public Campaign saveCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public List<Campaign> allCampaigns() {
        return campaignRepository.findAll();
    }

    public Optional<Campaign> singleCampaign(ObjectId id) {
        return campaignRepository.findById(id);
    }

    public void deleteCampaignById(ObjectId id) {
        campaignRepository.deleteById(id);
    }
}
