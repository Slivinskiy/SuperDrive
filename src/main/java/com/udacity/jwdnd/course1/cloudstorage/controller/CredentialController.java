package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Controller handling credential operations.
 */
@Slf4j
@Controller
@RequestMapping("/credentials")
public class CredentialController {

    // View names
    private static final String RESULT_VIEW = "result";
    
    // Model attribute keys
    private static final String ATTR_SUCCESS_MESSAGE = "successMessage";
    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    
    // Encryption key length
    private static final int ENCRYPTION_KEY_LENGTH = 16;
    
    // Success messages
    private static final String SUCCESS_CREDENTIAL_CREATED = "The credential successfully created.";
    private static final String SUCCESS_CREDENTIAL_UPDATED = "The credential successfully edited.";
    private static final String SUCCESS_CREDENTIAL_DELETED = "The credential has been deleted.";
    
    // Error messages
    private static final String ERROR_CREDENTIAL_CREATE = "There was an error creating the credential. Please try again.";
    private static final String ERROR_CREDENTIAL_UPDATE = "There was an error updating the credential. Please try again.";
    private static final String ERROR_CREDENTIAL_DELETE = "There was an error deleting the credential. Please try again.";

    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;
    private final UserService userService;

    public CredentialController(final CredentialsService credentialsService, final EncryptionService encryptionService, final UserService userService) {
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    /**
     * Creates or updates a credential.
     *
     * @param credential the credential data from the form
     * @param model the model for messages
     * @param authentication the authenticated user
     * @return the result view name
     */
    @PostMapping
    public String createOrUpdateCredential(@ModelAttribute("credential") final Credential credential, final Model model, final Authentication authentication) {
        final var username = authentication.getName();
        final var userId = userService.getUserByUsername(username).getUserId();
        credential.setUserId(userId);

        // Generate encryption key
        final var encodedKey = generateEncryptionKey();
        final var encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        final var isUpdate = credential.getCredentialId() != null && credential.getCredentialId() > 0;

        if (isUpdate) {
            log.info("Updating credential ID: {} for user: {}", credential.getCredentialId(), username);
            final var result = credentialsService.updateCredential(credential);
            
            if (result > 0) {
                model.addAttribute(ATTR_SUCCESS_MESSAGE, SUCCESS_CREDENTIAL_UPDATED);
            } else {
                log.error("Failed to update credential ID: {}", credential.getCredentialId());
                model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_CREDENTIAL_UPDATE);
            }
        } else {
            log.info("Creating new credential for user: {}", username);
            final var result = credentialsService.addCredential(credential);
            
            if (result > 0) {
                model.addAttribute(ATTR_SUCCESS_MESSAGE, SUCCESS_CREDENTIAL_CREATED);
            } else {
                log.error("Failed to create credential for user: {}", username);
                model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_CREDENTIAL_CREATE);
            }
        }
        
        return RESULT_VIEW;
    }

    /**
     * Deletes a credential by its ID.
     *
     * @param credentialId the ID of the credential to delete
     * @param model the model for messages
     * @return the result view name
     */
    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable final Integer credentialId, final Model model) {
        log.info("Deleting credential ID: {}", credentialId);
        
        final var result = credentialsService.deleteCredential(credentialId);
        
        if (result > 0) {
            model.addAttribute(ATTR_SUCCESS_MESSAGE, SUCCESS_CREDENTIAL_DELETED);
        } else {
            log.error("Failed to delete credential ID: {}", credentialId);
            model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_CREDENTIAL_DELETE);
        }
        
        return RESULT_VIEW;
    }

    /**
     * Generates a random encryption key for credential password encryption.
     *
     * @return Base64-encoded encryption key
     */
    private String generateEncryptionKey() {
        final var random = new SecureRandom();
        final var key = new byte[ENCRYPTION_KEY_LENGTH];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
