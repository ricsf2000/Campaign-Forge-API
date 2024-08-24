package com.thecampaignforge.campaignforge.dtos;

import com.thecampaignforge.campaignforge.models.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapDTO {
    private String id;
    private String campaignId;
    private String name;
    private String imageUrl;
    private List<NodeDTO> nodes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NodeDTO {
        private String id;
        private String title;
        private String description;
        private List<String> npcs;
        private String notableCharacteristics;
        private String questHooks;
        private double x;
        private double y;

        public static NodeDTO fromNode(Map.Node node) {
            return new NodeDTO(
                    node.getId().toString(),
                    node.getTitle(),
                    node.getDescription(),
                    node.getNpcs(),
                    node.getNotableCharacteristics(),
                    node.getQuestHooks(),
                    node.getX(),
                    node.getY()
            );
        }
    }

    public static MapDTO fromMap(Map map) {
        return new MapDTO(
                map.getId().toString(),
                map.getCampaignId(),
                map.getName(),
                map.getImageUrl(),
                map.getNodes().stream()
                        .map(NodeDTO::fromNode)
                        .collect(Collectors.toList())
        );
    }
}
