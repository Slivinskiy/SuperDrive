package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private Logger logger = LoggerFactory.getLogger(CredentialController.class);
    private CredentialsService credentialsService;
    private EncryptionService encryptionService;
    private UserService userService;

    public CredentialController(CredentialsService credentialsService, EncryptionService encryptionService, UserService userService) {
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @PostMapping
    public String PostOrEditCredential(@ModelAttribute("credential") Credential credential,  Model model, Authentication authentication){
        String username = authentication.getName();
        Integer userid = this.userService.getUserByUsername(username).getUserId();
        credential.setUserId(userid);
        String errorMessage = null;

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        if (credential.getCredentialId() > 0){
            int rowsAffected = credentialsService.updateCredential(credential);
            if (rowsAffected < 0){
               errorMessage = "There was an error updating the credential. Please, try again";
            }
            if (errorMessage == null){
                model.addAttribute("successMessage", "The Credential successfully edited");
            } else {
                model.addAttribute("errorMessage", errorMessage);
            }
        } else {

            int rowsAdded = credentialsService.AddCredential(credential);
            if (rowsAdded < 0){
                errorMessage = "There was an error creating the credential. Please, try again.";
            }
            if (errorMessage == null){
                model.addAttribute("successMessage", "The credential successfully created");
            } else {
                model.addAttribute("errorMessage", errorMessage);
            }
        }
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@ModelAttribute("credential") Credential credential, Model model){
        int  rowsAffected = credentialsService.deleteCredential(credential.getCredentialId());
        String errorMessage = null;
        if (rowsAffected < 0){
            errorMessage = "There was an error deleting the credential. Please, try again";
        }
        if (errorMessage == null){
            model.addAttribute("successMessage", "The credential has been deleted");
        }else {
            model.addAttribute("errorMessage",errorMessage);
        }

        return "result";
    }
}
