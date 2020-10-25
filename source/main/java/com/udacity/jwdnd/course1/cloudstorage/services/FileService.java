package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("Creating FileService bean");
    }


    public int uploadFile(File file){
        return  fileMapper.uploadFile(file);
    }

    public boolean IsFileNameAvailable(String fileName){
        return fileMapper.getFileByName(fileName) == null;
    }

    public List<File> getAllFiles(Integer userId){
       return fileMapper.getAllFiles(userId);
    }

    public File getFileByName(String fileName){
        return fileMapper.getFileByName(fileName);
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFileById(fileId);
    }

    public int delete( Integer fileId){
       return fileMapper.delete(fileId);
    }


}
