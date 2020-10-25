package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NotesService {

    private final NoteMapper noteMapper;

    public NotesService(NoteMapper noteMapper){
        this.noteMapper=noteMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NotesService bean");
    }

    public int addNote(Note note) {
        return noteMapper.addNote(note);
    }

    public int updateNote(Note note) {
      return noteMapper.updateNote(note);

    }

    public Note getById(Integer noteId) {
        return noteMapper.findById(noteId);
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }

    public List<Note> getAllNotes(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }
}
