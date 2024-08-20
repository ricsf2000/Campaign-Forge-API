package com.thecampaignforge.campaignforge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    private ObjectId id;
    private ObjectId campaignId;
    private String userId;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
