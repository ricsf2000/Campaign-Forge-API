package com.thecampaignforge.campaignforge.controllers;

import com.thecampaignforge.campaignforge.dtos.NoteDTO;
import com.thecampaignforge.campaignforge.models.Note;
import com.thecampaignforge.campaignforge.services.NoteService;
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
@RequestMapping("/api/v1/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<NoteDTO>> getNotesByCampaignId(@PathVariable String campaignId, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        List<Note> notes = noteService.getNotesByCampaignId(new ObjectId(campaignId), userId);

        List<NoteDTO> noteDTOs = notes.stream()
                .map(note -> new NoteDTO(
                        note.getId().toString(),
                        note.getCampaignId().toString(),
                        note.getUserId(),
                        note.getTitle(),
                        note.getContent(),
                        note.getCreatedAt(),
                        note.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(noteDTOs);
    }

    @PostMapping("/campaign/{campaignId}")
    public ResponseEntity<NoteDTO> createNote(@PathVariable String campaignId, @AuthenticationPrincipal Jwt jwt, @RequestBody NoteDTO noteDTO) {
        String userId = jwt.getSubject();

        Note note = new Note(
                new ObjectId(),
                new ObjectId(campaignId),
                userId,
                noteDTO.getTitle(),
                noteDTO.getContent(),
                new Date(),
                new Date()
        );

        Note createdNote = noteService.saveNoteForCampaign(new ObjectId(campaignId), note);

        NoteDTO createdNoteDTO = new NoteDTO(
                createdNote.getId().toString(),
                createdNote.getCampaignId().toString(),
                createdNote.getUserId(),
                createdNote.getTitle(),
                createdNote.getContent(),
                createdNote.getCreatedAt(),
                createdNote.getUpdatedAt()
        );

        return new ResponseEntity<>(createdNoteDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable String noteId, @AuthenticationPrincipal Jwt jwt, @RequestBody NoteDTO noteDTO) {
        String userId = jwt.getSubject();
        Optional<Note> existingNote = noteService.getNoteByIdAndUserId(new ObjectId(noteId), userId);

        if (existingNote.isPresent()) {
            Note note = existingNote.get();
            note.setTitle(noteDTO.getTitle());
            note.setContent(noteDTO.getContent());
            note.setUpdatedAt(new Date());

            Note updatedNote = noteService.saveNoteForCampaign(note.getCampaignId(), note);

            NoteDTO updatedNoteDTO = new NoteDTO(
                    updatedNote.getId().toString(),
                    updatedNote.getCampaignId().toString(),
                    updatedNote.getUserId(),
                    updatedNote.getTitle(),
                    updatedNote.getContent(),
                    updatedNote.getCreatedAt(),
                    updatedNote.getUpdatedAt()
            );

            return ResponseEntity.ok(updatedNoteDTO);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable String noteId, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        Optional<Note> existingNote = noteService.getNoteByIdAndUserId(new ObjectId(noteId), userId);

        if (existingNote.isPresent()) {
            noteService.deleteNoteById(new ObjectId(noteId), userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
