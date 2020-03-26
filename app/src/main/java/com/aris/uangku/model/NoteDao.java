package com.aris.uangku.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("select * from notes_table")
    LiveData<List<Note>> getAllNotes();


    @Query("select * from notes_table where category_id=:categoryId")
    LiveData<List<Note>> getNotes(int categoryId);


}
