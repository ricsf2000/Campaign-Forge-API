package com.thecampaignforge.campaignforge.services;

import com.thecampaignforge.campaignforge.models.Note;
import com.thecampaignforge.campaignforge.repositories.NoteRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note saveNoteForCampaign(ObjectId campaignId, Note note) {
        note.setCampaignId(campaignId);
        note.setCreatedAt(new Date());
        note.setUpdatedAt(new Date());
        return noteRepository.save(note);
    }

    public Optional<Note> getNoteByIdAndUserId(ObjectId id, String userId) {
        return noteRepository.findById(id).filter(note -> note.getUserId().equals(userId));
    }

    public List<Note> getNotesByCampaignId(ObjectId campaignId, String userId) {
        return noteRepository.findByCampaignId(campaignId);
    }

    public Optional<Note> getNoteById(ObjectId id) {
        return noteRepository.findById(id);
    }

    public Note createNote(Note note) {
        note.setCreatedAt(new Date());
        note.setUpdatedAt(new Date());
        return noteRepository.save(note);
    }

    public Optional<Note> updateNote(ObjectId id, Note updatedNote) {
        return noteRepository.findById(id).map(existingNote -> {
            existingNote.setTitle(updatedNote.getTitle());
            existingNote.setContent(updatedNote.getContent());
            existingNote.setUpdatedAt(new Date());
            return noteRepository.save(existingNote);
        });
    }

    public void deleteNoteById(ObjectId id, String userId) {
        noteRepository.deleteById(id);
    }
}
