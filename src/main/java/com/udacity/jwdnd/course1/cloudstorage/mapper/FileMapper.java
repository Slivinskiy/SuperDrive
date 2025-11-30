package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES where userid=#{userId}")
    List<File> getAllFiles(Integer userId);

    @Insert("INSERT INTO FILES ( filename, contenttype, filesize, userid, filedata)" +
            "VALUES(  #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Select("SELECT * FROM FILES WHERE filename=#{fileName}")
    File getFileByName(String fileName);

    @Select("SELECT * FROM FILES WHERE fileid=#{fileId}")
    File getFileById(Integer fileId);

    @Delete("DELETE FROM FILES Where  fileid=#{fileId}")
    int delete(Integer fileId);
}