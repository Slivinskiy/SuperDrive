package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for note management operations.
 * Handles CRUD operations for user notes.
 */
@Slf4j
@Service
public class NotesService {

    private final NoteMapper noteMapper;

    public NotesService(final NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * Creates a new note.
     *
     * @param note the note to create
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int addNote(final Note note) {
        log.info("Adding new note for user ID: {}", note.getUserId());
        final var result = noteMapper.addNote(note);
        log.debug("Note creation result: {}", result);
        return result;
    }

    /**
     * Updates an existing note.
     *
     * @param note the note with updated information
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int updateNote(final Note note) {
        log.info("Updating note ID: {} for user ID: {}", note.getNoteId(), note.getUserId());
        final var result = noteMapper.updateNote(note);
        log.debug("Note update result: {}", result);
        return result;
    }

    /**
     * Retrieves a note by its ID.
     *
     * @param noteId the ID of the note to retrieve
     * @return the Note object, or null if not found
     */
    public Note getNoteById(final Integer noteId) {
        log.debug("Retrieving note with ID: {}", noteId);
        return noteMapper.findById(noteId);
    }

    /**
     * Deletes a note by its ID.
     *
     * @param noteId the ID of the note to delete
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int deleteNote(final Integer noteId) {
        log.info("Deleting note ID: {}", noteId);
        final var result = noteMapper.deleteNote(noteId);
        log.debug("Note deletion result: {}", result);
        return result;
    }

    /**
     * Retrieves all notes for a specific user.
     *
     * @param userId the ID of the user whose notes to retrieve
     * @return list of notes belonging to the user
     */
    public List<Note> getAllNotes(final Integer userId) {
        log.debug("Retrieving all notes for user ID: {}", userId);
        return noteMapper.getAllNotes(userId);
    }
}
