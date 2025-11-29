package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.swing.text.Document;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@Controller
public class FileController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, @ModelAttribute("file") File file, String fileName, Model model, Authentication authentication) throws IOException {
        String username = authentication.getName();
        Integer userid = this.userService.getUserByUsername(username).getUserId();
        file.setUserId(userid);

        String errorMessage = null;

        Long fileSize = fileUpload.getSize();

        if (fileSize > 1048576){
            errorMessage = "Your file is too big. Max file size 1 MB";
        }

        if(!fileService.IsFileNameAvailable(fileUpload.getOriginalFilename())){
            errorMessage = "there is already a file with that name";
        }


        boolean emptyFile = fileUpload.isEmpty();
        if (emptyFile){
            errorMessage = "Sorry, you didn`t select any file.";
        }

        if (errorMessage == null){
          int rowsAdded = fileService.uploadFile(new File( fileUpload.getOriginalFilename(), fileUpload.getContentType(),String.valueOf(fileUpload.getSize()), userid, fileUpload.getBytes()));
          if (rowsAdded < 0) {
              errorMessage = "There was an error uploading the file. Please, try again";
          }
        }

        if (errorMessage == null){
            model.addAttribute("successMessage", "The file was successfully uploaded.");
        }else {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "result";
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@ModelAttribute("file") File file, Model model){
        String errorMessage = null;

        int rowsAffected = fileService.delete(file.getFileId());
        if (rowsAffected < 0){
            errorMessage = "There was an error deleting the file. Please try again.";
        }
        if (errorMessage == null){
            model.addAttribute("successMessage", "The file han been deleted");
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "result";
    }

    @GetMapping("/file/view/{fileName}")
        public ResponseEntity downloadFromDB(@PathVariable String fileName) {
            File file = fileService.getFileByName(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getFileData());
        }
    }










