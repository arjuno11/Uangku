package com.aris.uangku.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "notes_table", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id", onDelete = CASCADE))
public class Note extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int noteId;
    @ColumnInfo(name = "note_name")
    private String noteName;
    @ColumnInfo(name = "unit_nominal")
    private String unitNominal;
    @ColumnInfo(name = "category_id")
    private int categoryId;

    public Note() {
    }

    public Note(int noteId, String noteName, String unitNominal, int categoryId) {
        this.noteId = noteId;
        this.noteName = noteName;
        this.unitNominal = unitNominal;
        this.categoryId = categoryId;
    }

    @Bindable
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
        notifyPropertyChanged(BR.noteId);
    }

    @Bindable
    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
        notifyPropertyChanged(BR.noteName);
    }

    @Bindable
    public String getUnitNominal() {
        return unitNominal;
    }

    public void setUnitNominal(String unitNominal) {
        this.unitNominal = unitNominal;
        notifyPropertyChanged(BR.unitNominal);
    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return noteId == note.noteId &&
                categoryId == note.categoryId &&
                noteName.equals(note.noteName) &&
                unitNominal.equals(note.unitNominal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, noteName, unitNominal, categoryId);
    }
}
