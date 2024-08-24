package com.thecampaignforge.campaignforge.controllers;

import com.thecampaignforge.campaignforge.dtos.MapDTO;
import com.thecampaignforge.campaignforge.models.Map;
import com.thecampaignforge.campaignforge.services.MapService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/maps")
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<MapDTO>> getMapsByCampaignId(@PathVariable String campaignId) {
        List<Map> maps = mapService.getMapsByCampaignId(campaignId);

        List<MapDTO> mapDTOs = maps.stream()
                .map(MapDTO::fromMap)
                .collect(Collectors.toList());

        return ResponseEntity.ok(mapDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MapDTO> getMapById(@PathVariable String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Map> map = mapService.getMapById(objectId);

        if (map.isPresent()) {
            return new ResponseEntity<>(MapDTO.fromMap(map.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/campaign/{campaignId}")
    public ResponseEntity<MapDTO> createMap(@PathVariable String campaignId, @RequestBody Map map) {
        map.setCampaignId(campaignId);
        Map createdMap = mapService.saveMap(map);
        return new ResponseEntity<>(MapDTO.fromMap(createdMap), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MapDTO> updateMap(@PathVariable String id, @RequestBody Map updatedMapData) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Map> mapOptional = mapService.getMapById(objectId);

        if (mapOptional.isPresent()) {
            Map existingMap = mapOptional.get();

            existingMap.setName(updatedMapData.getName());
            existingMap.setImageUrl(updatedMapData.getImageUrl());
            existingMap.setNodes(updatedMapData.getNodes());

            Map updatedMap = mapService.saveMap(existingMap);

            return new ResponseEntity<>(MapDTO.fromMap(updatedMap), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Map> map = mapService.getMapById(objectId);

        if (map.isPresent()) {
            mapService.deleteMapById(objectId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{mapId}/nodes")
    public ResponseEntity<MapDTO> addNodeToMap(@PathVariable String mapId, @RequestBody Map.Node node) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(mapId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Map> mapOptional = mapService.addNodeToMap(objectId, node);
        if (mapOptional.isPresent()) {
            return new ResponseEntity<>(MapDTO.fromMap(mapOptional.get()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{mapId}/nodes/{nodeId}")
    public ResponseEntity<MapDTO> updateNodeInMap(@PathVariable String mapId, @PathVariable String nodeId, @RequestBody Map.Node updatedNode) {
        ObjectId mapObjectId;
        ObjectId nodeObjectId;
        try {
            mapObjectId = new ObjectId(mapId);
            nodeObjectId = new ObjectId(nodeId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Map> mapOptional = mapService.updateNodeInMap(mapObjectId, nodeObjectId, updatedNode);
        if (mapOptional.isPresent()) {
            return new ResponseEntity<>(MapDTO.fromMap(mapOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{mapId}/nodes/{nodeId}")
    public ResponseEntity<MapDTO> deleteNodeFromMap(@PathVariable String mapId, @PathVariable String nodeId) {
        ObjectId mapObjectId;
        ObjectId nodeObjectId;
        try {
            mapObjectId = new ObjectId(mapId);
            nodeObjectId = new ObjectId(nodeId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Map> mapOptional = mapService.deleteNodeFromMap(mapObjectId, nodeObjectId);
        if (mapOptional.isPresent()) {
            return new ResponseEntity<>(MapDTO.fromMap(mapOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

