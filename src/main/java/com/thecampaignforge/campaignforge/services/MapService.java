package com.thecampaignforge.campaignforge.services;

import com.thecampaignforge.campaignforge.models.Map;
import com.thecampaignforge.campaignforge.repositories.MapRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    public List<Map> getMapsByCampaignId(String campaignId) {
        return mapRepository.findByCampaignId(campaignId);
    }

    public Optional<Map> getMapById(ObjectId id) {
        return mapRepository.findById(id);
    }

    public Map saveMap(Map map) {
        return mapRepository.save(map);
    }

    public void deleteMapById(ObjectId id) {
        mapRepository.deleteById(id);
    }

    public Optional<Map> addNodeToMap(ObjectId mapId, Map.Node node) {
        Optional<Map> mapOptional = mapRepository.findById(mapId);
        if (mapOptional.isPresent()) {
            Map map = mapOptional.get();
            node.setId(new ObjectId());
            map.getNodes().add(node);
            mapRepository.save(map);
            return Optional.of(map);
        }
        return Optional.empty();
    }

    public Optional<Map> updateNodeInMap(ObjectId mapId, ObjectId nodeId, Map.Node updatedNode) {
        Optional<Map> mapOptional = mapRepository.findById(mapId);
        if (mapOptional.isPresent()) {
            Map map = mapOptional.get();
            map.getNodes().removeIf(node -> node.getId().equals(nodeId));
            map.getNodes().add(updatedNode);
            mapRepository.save(map);
            return Optional.of(map);
        }
        return Optional.empty();
    }

    public Optional<Map> deleteNodeFromMap(ObjectId mapId, ObjectId nodeId) {
        Optional<Map> mapOptional = mapRepository.findById(mapId);
        if (mapOptional.isPresent()) {
            Map map = mapOptional.get();
            map.getNodes().removeIf(node -> node.getId().equals(nodeId));
            mapRepository.save(map);
            return Optional.of(map);
        }
        return Optional.empty();
    }
}
