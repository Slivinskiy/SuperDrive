package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Service layer for user management operations.
 * Handles user creation, retrieval, and username availability checks.
 */
@Slf4j
@Service
public class UserService {
    private static final int SALT_LENGTH = 16;

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(final UserMapper userMapper, final HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * Checks if a username is available for registration.
     *
     * @param username the username to check
     * @return true if the username is available, false otherwise
     */
    public boolean isUsernameAvailable(final String username) {
        log.debug("Checking username availability for: {}", username);
        return userMapper.getUserByUsername(username) == null;
    }

    /**
     * Creates a new user with hashed password.
     * Generates a random salt for password hashing and stores the user in the database.
     *
     * @param user the user to create (password will be hashed)
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int createUser(final User user) {
        log.info("Creating new user: {}", user.getUsername());
        
        final var encodedSalt = generateSalt();
        final var hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        
        final var userToInsert = new User(
            null,
            user.getUsername(),
            encodedSalt,
            hashedPassword,
            user.getFirstName(),
            user.getLastName()
        );
        
        final var result = userMapper.insert(userToInsert);
        log.debug("User creation result for {}: {}", user.getUsername(), result);
        
        return result;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User object, or null if not found
     */
    public User getUser(final Integer userId) {
        log.debug("Retrieving user with ID: {}", userId);
        return userMapper.getUser(userId);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the User object, or null if not found
     */
    public User getUserByUsername(final String username) {
        log.debug("Retrieving user with username: {}", username);
        return userMapper.getUserByUsername(username);
    }

    /**
     * Generates a random salt for password hashing.
     *
     * @return Base64-encoded salt string
     */
    private String generateSalt() {
        final var random = new SecureRandom();
        final var salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
