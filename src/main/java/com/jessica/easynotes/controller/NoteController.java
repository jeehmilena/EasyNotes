package com.jessica.easynotes.controller;


import com.jessica.easynotes.exception.ResourceNotFoundException;
import com.jessica.easynotes.model.Note;
import com.jessica.easynotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;


    //Get all notes
    @GetMapping("/notes")
    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    //Create a new note
    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note){
        return noteRepository.save(note);
    }

    //Get a single note
    @GetMapping("notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId){
        return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    //Update the note
    @PutMapping("notes/{id}")
    public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails){
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        Note updateNote = noteRepository.save(note);
        return updateNote;
    }

    //Delete note
    @DeleteMapping("notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId){
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);

        return ResponseEntity.ok().build();


    }
}
