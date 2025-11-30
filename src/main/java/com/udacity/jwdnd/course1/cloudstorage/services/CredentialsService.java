package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for credential management operations.
 * Handles CRUD operations for user credentials.
 */
@Slf4j
@Service
public class CredentialsService {

    private final CredentialMapper credentialMapper;

    public CredentialsService(final CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    /**
     * Creates a new credential.
     *
     * @param credential the credential to create
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int addCredential(final Credential credential) {
        log.info("Adding new credential for user ID: {}", credential.getUserId());
        final var result = credentialMapper.addCredential(credential);
        log.debug("Credential creation result: {}", result);
        return result;
    }

    /**
     * Updates an existing credential.
     *
     * @param credential the credential with updated information
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int updateCredential(final Credential credential) {
        log.info("Updating credential ID: {} for user ID: {}", credential.getCredentialId(), credential.getUserId());
        final var result = credentialMapper.updateCredential(credential);
        log.debug("Credential update result: {}", result);
        return result;
    }

    /**
     * Deletes a credential by its ID.
     *
     * @param credentialId the ID of the credential to delete
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int deleteCredential(final Integer credentialId) {
        log.info("Deleting credential ID: {}", credentialId);
        final var result = credentialMapper.deleteCredential(credentialId);
        log.debug("Credential deletion result: {}", result);
        return result;
    }

    /**
     * Retrieves all credentials for a specific user.
     *
     * @param userId the ID of the user whose credentials to retrieve
     * @return list of credentials belonging to the user
     */
    public List<Credential> getAllCredentials(final Integer userId) {
        log.debug("Retrieving all credentials for user ID: {}", userId);
        return credentialMapper.getAllCredentials(userId);
    }
}
