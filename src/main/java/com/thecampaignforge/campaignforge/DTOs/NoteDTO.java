package com.thecampaignforge.campaignforge.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class NoteDTO {
    private String id;
    private String campaignId;
    private String userId;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
