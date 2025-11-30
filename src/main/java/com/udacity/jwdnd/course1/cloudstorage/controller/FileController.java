package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller handling file operations.
 */
@Slf4j
@Controller
public class FileController {

    // View names
    private static final String RESULT_VIEW = "result";
    
    // Model attribute keys
    private static final String ATTR_SUCCESS_MESSAGE = "successMessage";
    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    
    // File size limit (1 MB)
    private static final long MAX_FILE_SIZE = 1048576L;
    
    // Success messages
    private static final String SUCCESS_FILE_UPLOADED = "The file was successfully uploaded.";
    private static final String SUCCESS_FILE_DELETED = "The file has been deleted.";
    
    // Error messages
    private static final String ERROR_FILE_TOO_BIG = "Your file is too big. Max file size 1 MB.";
    private static final String ERROR_FILE_EXISTS = "There is already a file with that name.";
    private static final String ERROR_FILE_EMPTY = "Sorry, you didn't select any file.";
    private static final String ERROR_FILE_UPLOAD = "There was an error uploading the file. Please try again.";
    private static final String ERROR_FILE_DELETE = "There was an error deleting the file. Please try again.";

    private final FileService fileService;
    private final UserService userService;

    public FileController(final FileService fileService, final UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    /**
     * Handles file upload.
     *
     * @param fileUpload the uploaded file
     * @param model the model for messages
     * @param authentication the authenticated user
     * @return the result view name
     * @throws IOException if file reading fails
     */
    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") final MultipartFile fileUpload, final Model model, final Authentication authentication) throws IOException {
        final var username = authentication.getName();
        final var userId = userService.getUserByUsername(username).getUserId();
        
        log.info("File upload attempt by user: {}", username);

        // Validate file
        if (fileUpload.isEmpty()) {
            log.warn("Empty file upload attempt by user: {}", username);
            model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_FILE_EMPTY);
            return RESULT_VIEW;
        }

        if (fileUpload.getSize() > MAX_FILE_SIZE) {
            log.warn("File too large: {} bytes by user: {}", fileUpload.getSize(), username);
            model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_FILE_TOO_BIG);
            return RESULT_VIEW;
        }

        if (!fileService.isFileNameAvailable(fileUpload.getOriginalFilename())) {
            log.warn("Duplicate filename: {} by user: {}", fileUpload.getOriginalFilename(), username);
            model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_FILE_EXISTS);
            return RESULT_VIEW;
        }

        // Create and upload file
        final var newFile = new File();
        newFile.setFileName(fileUpload.getOriginalFilename());
        newFile.setContentType(fileUpload.getContentType());
        newFile.setFileSize(String.valueOf(fileUpload.getSize()));
        newFile.setUserId(userId);
        newFile.setFileData(fileUpload.getBytes());
        
        final var result = fileService.uploadFile(newFile);
        
        if (result > 0) {
            log.info("File uploaded successfully: {} by user: {}", fileUpload.getOriginalFilename(), username);
            model.addAttribute(ATTR_SUCCESS_MESSAGE, SUCCESS_FILE_UPLOADED);
        } else {
            log.error("Failed to upload file: {} by user: {}", fileUpload.getOriginalFilename(), username);
            model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_FILE_UPLOAD);
        }

        return RESULT_VIEW;
    }

    /**
     * Deletes a file by its ID.
     *
     * @param fileId the ID of the file to delete
     * @param model the model for messages
     * @return the result view name
     */
    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable final Integer fileId, final Model model) {
        log.info("Deleting file ID: {}", fileId);
        
        final var result = fileService.delete(fileId);
        
        if (result > 0) {
            model.addAttribute(ATTR_SUCCESS_MESSAGE, SUCCESS_FILE_DELETED);
        } else {
            log.error("Failed to delete file ID: {}", fileId);
            model.addAttribute(ATTR_ERROR_MESSAGE, ERROR_FILE_DELETE);
        }

        return RESULT_VIEW;
    }

    /**
     * Downloads a file by its name.
     *
     * @param fileName the name of the file to download
     * @return ResponseEntity containing the file data
     */
    @GetMapping("/file/view/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable final String fileName) {
        log.info("Downloading file: {}", fileName);
        
        final var file = fileService.getFileByName(fileName);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }
}










