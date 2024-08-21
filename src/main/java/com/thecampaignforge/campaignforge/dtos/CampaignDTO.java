package com.thecampaignforge.campaignforge.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CampaignDTO {
    private String id;
    private String userId;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
