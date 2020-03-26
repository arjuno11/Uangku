package com.aris.uangku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.aris.uangku.databinding.ActivityAddAndEditBinding;
import com.aris.uangku.model.Note;

public class AddAndEditActivity extends AppCompatActivity {

    private Note note;
    public static final String NOTE_ID = "noteId";
    public static final String NOTE_NAME = "noteName";
    public static final String UNIT_NOMINAL = "unitNominal";


    private ActivityAddAndEditBinding activityAddAndEditBinding;
    private AddAndEditActivityClickHandlers addAndEditActivityClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);

        note = new Note();
        activityAddAndEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_and_edit);
        activityAddAndEditBinding.setNote(note);

        addAndEditActivityClickHandlers = new AddAndEditActivityClickHandlers(this);
        activityAddAndEditBinding.setAddAndEditActivityClickHandlers(addAndEditActivityClickHandlers);

        Intent intent = getIntent();
        if (intent.hasExtra(NOTE_ID)) {
            setTitle("Edit Note");
            note.setNoteName(intent.getStringExtra(NOTE_NAME));
            note.setUnitNominal(intent.getStringExtra(UNIT_NOMINAL));
        } else {
            setTitle("Add Note");
        }


    }

    public class AddAndEditActivityClickHandlers {
        Context context;

        public AddAndEditActivityClickHandlers(Context context) {
            this.context = context;
        }


        public void onSubmitButtonClicked(View view) {
            if (note.getNoteName() == null) {
                Toast.makeText(context, "name field cant be empty", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent=new Intent();
                intent.putExtra(NOTE_NAME, note.getNoteName());
                intent.putExtra(UNIT_NOMINAL, note.getUnitNominal());
                setResult(RESULT_OK,intent);
                finish();

            }
        }

    }

}
