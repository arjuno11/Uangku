package com.aris.uangku;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.aris.uangku.model.Note;

import java.util.ArrayList;

public class NoteDiffCallback extends DiffUtil.Callback {

    private ArrayList<Note> oldNoteList;
    private ArrayList<Note> newNoteList;

    public NoteDiffCallback(ArrayList<Note> oldNoteList, ArrayList<Note> newNoteList) {
        this.oldNoteList = oldNoteList;
        this.newNoteList = newNoteList;
    }

    @Override
    public int getOldListSize() {
        return oldNoteList == null ? 0 : oldNoteList.size();
    }

    @Override
    public int getNewListSize() {
        return newNoteList == null ? 0 : newNoteList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNoteList.get(oldItemPosition).getNoteId() == newNoteList.get(newItemPosition).getNoteId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNoteList.get(oldItemPosition).equals(newNoteList.get(newItemPosition).getNoteId());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
