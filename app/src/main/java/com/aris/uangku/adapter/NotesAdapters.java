package com.aris.uangku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aris.uangku.NoteDiffCallback;
import com.aris.uangku.R;
import com.aris.uangku.databinding.NoteListItemBinding;
import com.aris.uangku.model.Note;

import java.util.ArrayList;

public class NotesAdapters extends RecyclerView.Adapter<NotesAdapters.NoteViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<Note> noteArrayList = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteListItemBinding noteListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.note_list_item, parent, false);
        return new NoteViewHolder(noteListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteArrayList.get(position);
        holder.noteListItemBinding.setNote(note);

    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        NoteListItemBinding noteListItemBinding;


        public NoteViewHolder(@NonNull NoteListItemBinding noteListItemBinding) {
            super(noteListItemBinding.getRoot());
            this.noteListItemBinding = noteListItemBinding;
            noteListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickPosition = getAdapterPosition();
                    if (onItemClickListener != null && clickPosition != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(noteArrayList.get(clickPosition));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setNoteArrayList(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
        notifyDataSetChanged();
    }

    public void setNotes(ArrayList<Note> newNoteArrayList) {
         DiffUtil.DiffResult result =DiffUtil.calculateDiff(new NoteDiffCallback(noteArrayList, newNoteArrayList),false);
         noteArrayList = newNoteArrayList;
         result.dispatchUpdatesTo(NotesAdapters.this);

    }



}
