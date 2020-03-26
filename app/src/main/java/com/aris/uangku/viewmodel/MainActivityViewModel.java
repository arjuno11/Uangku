package com.aris.uangku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aris.uangku.model.Note;
import com.aris.uangku.model.Category;
import com.aris.uangku.model.NoteRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Note>> notesOfSelectedCategory;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = noteRepository.getCategories();
        return allCategories;
    }

    public LiveData<List<Note>> getNotesOfSelectedCategory(int categoryId) {
        notesOfSelectedCategory = noteRepository.getNotes(categoryId);
        return notesOfSelectedCategory;
    }


    public void addNewNote(Note note) {
        noteRepository.insertNote(note);
    }

    public void updateNote(Note note) {
        noteRepository.updateNote(note);
    }

    public void deleteNote(Note note) {
        noteRepository.deleteNote(note);
    }


}
