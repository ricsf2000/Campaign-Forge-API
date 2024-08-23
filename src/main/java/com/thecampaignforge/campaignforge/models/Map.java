package com.thecampaignforge.campaignforge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "maps")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Map {
    @Id
    private ObjectId id;
    private String campaignId;
    private String name;
    private String imageUrl;
    private List<Node> nodes;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node {
        private ObjectId id;
        private String title;
        private String description;
        private List<String> npcs;
        private String notableCharacteristics;
        private String questHooks;
        private double x;
        private double y;
    }
}
