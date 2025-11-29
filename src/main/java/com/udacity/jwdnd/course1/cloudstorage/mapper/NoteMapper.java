package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} ")
     List<Note> getAllNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note findById(Integer noteid);

    @Insert("INSERT INTO NOTES ( noteTitle, noteDescription, userId)"+
            "VALUES(  #{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyColumn = "noteId", keyProperty = "noteId")

    int addNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES Where noteId = #{noteId} ")

    int deleteNote(Integer noteId);


}
