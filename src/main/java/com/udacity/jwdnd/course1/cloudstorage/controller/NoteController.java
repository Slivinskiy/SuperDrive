package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling note operations.
 */
@Slf4j
@Controller
@RequestMapping("/notes")
public class NoteController {

    // View names
    private static final String RESULT_VIEW = "result";
    
    // Model attribute keys
    private static final String ATTR_SUCCESS_MESSAGE = "successMessage";
    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    
    // Success messages
    private static final String SUCCESS_NOTE_CREATED = "Your note was successfully created.";
    private static final String SUCCESS_NOTE_UPDATED = "Note successfully edited.";
    private static final String SUCCESS_NOTE_DELETED = "The note has been deleted.";
    
    // Error messages
    private static final String ERROR_NOTE_CREATE = "There was an error adding the note. Please try again.";
    private static final String ERROR_NOTE_UPDATE = "There was an error updating the note. Please try again.";
    private static final String ERROR_NOTE_DELETE = "There was an error deleting the note. Please try again.";

    private final NotesService notesService;
    private final UserService userService;

    public NoteController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    /**
     * Creates or updates a note.
     *
     * @param note the note data from the form
     * @param authentication the authenticated user
     * @param model the model for messages
     * @return the result view name
     */
    @PostMapping
    public String createOrUpdateNote(@ModelAttribute("note") final Note note, final Authentication authentication, final Model model) {
        final var username = authentication.getName();
        final var userId = userService.getUserByUsername(username).getUserId();
        note.setUserId(userId);
        
        final var isUpdate = note.getNoteId() != null && note.getNoteId() > 0;
        
        if (isUpdate) {
            log.info("Updating note ID: {} for user: {}", note.getNoteId(), username);
            final var result = notesService.updateNote(note);
            
            if (result > 0) {
                model.addAttribute(ATTR_SUCCESS_MESSAGE, SUCCESS_NOTE_UPDATED);
            } else {
                log.error("Failed to update note ID: {}", note.getNoteId());
                model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_NOTE_UPDATE);
            }
        } else {
            log.info("Creating new note for user: {}", username);
            final var result = notesService.addNote(note);
            
            if (result > 0) {
                model.addAttribute(ATTR_SUCCESS_MESSAGE, SUCCESS_NOTE_CREATED);
            } else {
                log.error("Failed to create note for user: {}", username);
                model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_NOTE_CREATE);
            }
        }
        
        return RESULT_VIEW;
    }

    /**
     * Deletes a note by its ID.
     *
     * @param noteId the ID of the note to delete
     * @param model the model for messages
     * @return the result view name
     */
    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable final Integer noteId, final Model model) {
        log.info("Deleting note ID: {}", noteId);
        
        final var result = notesService.deleteNote(noteId);
        
        if (result > 0) {
            model.addAttribute(ATTR_SUCCESS_MESSAGE, SUCCESS_NOTE_DELETED);
        } else {
            log.error("Failed to delete note ID: {}", noteId);
            model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_NOTE_DELETE);
        }
        
        return RESULT_VIEW;
    }
}
