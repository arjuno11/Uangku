package com.aris.uangku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aris.uangku.adapter.NotesAdapters;
import com.aris.uangku.databinding.ActivityMainBinding;
import com.aris.uangku.model.Category;
import com.aris.uangku.model.Note;
import com.aris.uangku.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.aris.uangku.AddAndEditActivity.NOTE_ID;
import static com.aris.uangku.AddAndEditActivity.NOTE_NAME;
import static com.aris.uangku.AddAndEditActivity.UNIT_NOMINAL;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers handlers;

    private ArrayList<Category> categoryArrayList;
    private ArrayList<Note> noteArrayList;

    private Category selectedCategory;

    private RecyclerView recyclerView;
    private NotesAdapters notesAdapters;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private int selectedCategoryId;
    private int selectedNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandlers(handlers);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryArrayList = (ArrayList<Category>) categories;
                showOnSpinner();
            }
        });


    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, R.layout.support_simple_spinner_dropdown_item, categoryArrayList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);

    }

    private void loadNotesArrayList(int categoryId) {
        mainActivityViewModel.getNotesOfSelectedCategory(categoryId).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteArrayList = (ArrayList<Note>) notes;
                loadRecyclerView();
            }
        });
    }

    private void loadRecyclerView() {
        recyclerView = activityMainBinding.secondaryLayout.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        notesAdapters = new NotesAdapters();
        recyclerView.setAdapter(notesAdapters);
        //notesAdapters.setNoteArrayList(noteArrayList);
        notesAdapters.setNotes(noteArrayList);
        notesAdapters.setOnItemClickListener(new NotesAdapters.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                selectedNoteId = note.getNoteId();
                Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);
                intent.putExtra(NOTE_ID, selectedNoteId);
                intent.putExtra(NOTE_NAME, note.getNoteName());
                intent.putExtra(UNIT_NOMINAL, note.getUnitNominal());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Note note = noteArrayList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteNote(note);


            }
        }).attachToRecyclerView(recyclerView);

    }

    public class MainActivityClickHandlers {
        public void onFabClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        }


        public void onSelectedItem(AdapterView<?> parent, View view, int pos, long id) {
            selectedCategory = (Category) parent.getItemAtPosition(pos);
            Button floatingActionButton = (Button) findViewById(R.id.floatingActionButton);
            if (selectedCategory.getId() == 3) {
                floatingActionButton.setVisibility(View.GONE);
            } else {
                floatingActionButton.setVisibility(View.VISIBLE);
            }
            loadNotesArrayList(selectedCategory.getId());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        selectedCategoryId = selectedCategory.getId();

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {

            Note note = new Note();
            note.setCategoryId(selectedCategoryId);
            note.setNoteName(data.getStringExtra(NOTE_NAME));
            note.setUnitNominal(data.getStringExtra(UNIT_NOMINAL));
            mainActivityViewModel.addNewNote(note);

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            Note note = new Note();
            note.setCategoryId(selectedCategoryId);
            note.setNoteId(selectedNoteId);
            note.setNoteName(data.getStringExtra(NOTE_NAME));
            note.setUnitNominal(data.getStringExtra(UNIT_NOMINAL));
            mainActivityViewModel.updateNote(note);

        }


    }
}
