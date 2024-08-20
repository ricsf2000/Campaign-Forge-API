package com.thecampaignforge.campaignforge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "campaigns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {
    @Id
    private ObjectId id;
    private String userId;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}