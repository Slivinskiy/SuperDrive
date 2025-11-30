package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {
    private final UserService userService;
    private final NotesService notesService;
    private final FileService fileService;
    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;


    public HomeController(UserService userService, NotesService notesService, CredentialsService credentialsService, EncryptionService encryptionService, FileService fileService){
        this.userService = userService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, @ModelAttribute("file") File file, Authentication authentication, Model model){
        String username = authentication.getName();
        int userId = userService.getUserByUsername(username).getUserId();
        model.addAttribute("files", this.fileService.getAllFiles(userId));
        model.addAttribute("credentials",this.credentialsService.getAllCredentials(userId));
        model.addAttribute("notes",this.notesService.getAllNotes(userId));
        model.addAttribute("encryptionService", this.encryptionService);
        return "home";
    }
}
