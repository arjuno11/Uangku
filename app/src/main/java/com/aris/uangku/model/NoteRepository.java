package com.aris.uangku.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteRepository {
    private CategoryDao categoryDao;
    private NoteDao noteDao;

    private LiveData<List<Category>> categories;
    private LiveData<List<Note>> notes;


    public NoteRepository(Application application) {
        NotesDatabase notesDatabase = NotesDatabase.getInstance(application);
        categoryDao = notesDatabase.categoryDao();
        noteDao = notesDatabase.noteDao();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDao.getAllCategories();
    }

    public LiveData<List<Note>> getNotes(int categoryId) {
        return noteDao.getNotes(categoryId);
    }

    public void insertCategory(Category category) {
        new InsertCategoryAsyncTask(categoryDao).execute(category);
    }

    public void updateCategory(Category category) {
        new UpdateCategoryAsyncTask(categoryDao).execute(category);
    }

    public void deleteCategory(Category category) {
        new DeleteCategoryAsyncTask(categoryDao).execute(category);
    }

    public void insertNote(final Note note) {
        //new InsertNoteAsyncTask(noteDao).execute(note);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });


    }

    public void updateNote(final Note note) {
        //new UpdateNoteAsyncTask(noteDao).execute(note);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }


    private class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao categoryDao;

        InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insert(categories[0]);
            return null;
        }
    }

    private class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao categoryDao;

        DeleteCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.delete(categories[0]);
            return null;
        }
    }

    private class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao categoryDao;

        UpdateCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.update(categories[0]);
            return null;
        }
    }

    private class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }


}
