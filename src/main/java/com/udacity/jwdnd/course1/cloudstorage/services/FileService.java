package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for file management operations.
 * Handles file upload, download, and deletion operations.
 */
@Slf4j
@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(final FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * Uploads a file to the database.
     *
     * @param file the file to upload
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int uploadFile(final File file) {
        log.info("Uploading file: {} for user ID: {}", file.getFileName(), file.getUserId());
        final var result = fileMapper.uploadFile(file);
        log.debug("File upload result: {}", result);
        return result;
    }

    /**
     * Checks if a filename is available (not already used).
     *
     * @param fileName the filename to check
     * @return true if the filename is available, false otherwise
     */
    public boolean isFileNameAvailable(final String fileName) {
        log.debug("Checking filename availability: {}", fileName);
        return fileMapper.getFileByName(fileName) == null;
    }

    /**
     * Retrieves all files for a specific user.
     *
     * @param userId the ID of the user whose files to retrieve
     * @return list of files belonging to the user
     */
    public List<File> getAllFiles(final Integer userId) {
        log.debug("Retrieving all files for user ID: {}", userId);
        return fileMapper.getAllFiles(userId);
    }

    /**
     * Retrieves a file by its name.
     *
     * @param fileName the name of the file to retrieve
     * @return the File object, or null if not found
     */
    public File getFileByName(final String fileName) {
        log.debug("Retrieving file by name: {}", fileName);
        return fileMapper.getFileByName(fileName);
    }

    /**
     * Retrieves a file by its ID.
     *
     * @param fileId the ID of the file to retrieve
     * @return the File object, or null if not found
     */
    public File getFileById(final Integer fileId) {
        log.debug("Retrieving file by ID: {}", fileId);
        return fileMapper.getFileById(fileId);
    }

    /**
     * Deletes a file by its ID.
     *
     * @param fileId the ID of the file to delete
     * @return the number of rows affected (1 if successful, 0 or negative if failed)
     */
    public int delete(final Integer fileId) {
        log.info("Deleting file ID: {}", fileId);
        final var result = fileMapper.delete(fileId);
        log.debug("File deletion result: {}", result);
        return result;
    }
}
