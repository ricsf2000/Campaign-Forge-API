package com.thecampaignforge.campaignforge.controllers;

import com.thecampaignforge.campaignforge.dtos.CampaignDTO;
import com.thecampaignforge.campaignforge.services.CampaignService;
import com.thecampaignforge.campaignforge.models.Campaign;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        List<Campaign> campaigns = campaignService.getCampaignsByUserId(userId);

        List<CampaignDTO> campaignDTOs = campaigns.stream()
                .map(campaign -> new CampaignDTO(
                        campaign.getId().toString(),
                        campaign.getUserId(),
                        campaign.getName(),
                        campaign.getDescription(),
                        campaign.getCreatedAt(),
                        campaign.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(campaignDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDTO> getSingleCampaign(@PathVariable ObjectId id, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        Optional<Campaign> campaign = campaignService.getCampaignByIdAndUserId(id, userId);

        if (campaign.isPresent()) {
            CampaignDTO campaignDTO = new CampaignDTO(
                    campaign.get().getId().toString(),
                    campaign.get().getUserId(),
                    campaign.get().getName(),
                    campaign.get().getDescription(),
                    campaign.get().getCreatedAt(),
                    campaign.get().getUpdatedAt()
            );
            return new ResponseEntity<>(campaignDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@AuthenticationPrincipal Jwt jwt, @RequestBody Campaign campaign) {
        String userId = jwt.getSubject();
        campaign.setUserId(userId);
        campaign.setCreatedAt(new Date());
        campaign.setUpdatedAt(new Date());
        Campaign createdCampaign = campaignService.saveCampaign(campaign);
        return new ResponseEntity<>(createdCampaign, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable("id") String id, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Campaign> campaign = campaignService.getCampaignByIdAndUserId(objectId, userId);

        if (campaign.isPresent()) {
            campaignService.deleteCampaignById(objectId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable("id") String id,
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody Campaign updatedCampaignData) {

        String userId = jwt.getSubject();

        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Campaign> campaignOptional = campaignService.getCampaignByIdAndUserId(objectId, userId);

        if (campaignOptional.isPresent()) {
            Campaign existingCampaign = campaignOptional.get();

            existingCampaign.setName(updatedCampaignData.getName());
            existingCampaign.setDescription(updatedCampaignData.getDescription());
            existingCampaign.setUpdatedAt(new Date());

            Campaign updatedCampaign = campaignService.saveCampaign(existingCampaign);

            return new ResponseEntity<>(updatedCampaign, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
