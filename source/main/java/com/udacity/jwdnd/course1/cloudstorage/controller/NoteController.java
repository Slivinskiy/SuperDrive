package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

private NotesService notesService;
private UserService userService;

    public NoteController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;

    }

    @PostMapping
    public String PostOrUpdateNote(@ModelAttribute("note") Note note, Authentication authentication, User user, Model model){

        String username = authentication.getName();
        Integer userid = this.userService.getUserByUsername(username).getUserId();
        note.setUserId(userid);
        String errorMessage = null;

        int noteId = note.getNoteId();

        if (noteId > 0) {
            int rowsAffected = this.notesService.updateNote(note);

            if (rowsAffected < 0) {
                errorMessage = "There was an error updating the note. Please try again.";
            }

            if (errorMessage == null) {
                model.addAttribute("successMessage", "Note successfully edited ");
            } else {
                model.addAttribute("errorMessage", errorMessage);
            }

        }
        else {
            int rowsAdded = this.notesService.addNote(note);
            if (rowsAdded < 0) {
                errorMessage = "There was an error adding the note. Please try again.";
            }

            if (errorMessage == null) {
                model.addAttribute("successMessage", "Your note was successfully created.");
            } else {
                model.addAttribute("errorMessage", errorMessage);
            }

        }
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@ModelAttribute("note") Note note,  Model model){
        int rowsAffected = notesService.deleteNote( note.getNoteId());
        String errorMessage = null;

        if (rowsAffected<0){
            errorMessage = "There was an error deleting the note. Please, try again";

        }
        if (errorMessage==null){
            model.addAttribute("successMessage", "The note has been deleted");
        }
        else {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "result";
    }

}
