package com.aris.uangku.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Category.class, Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract NoteDao noteDao();

    private static NotesDatabase instance;

    public static synchronized NotesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class, "notes_database")
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static Callback callback =new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();


        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
    private static class InitialDataAsyncTask extends AsyncTask<Void,Void,Void> {
        private CategoryDao categoryDAO;
        private NoteDao noteDAO;

        public InitialDataAsyncTask(NotesDatabase notesDatabase) {

            categoryDAO= notesDatabase.categoryDao();
            noteDAO = notesDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Category category1=new Category();
            category1.setCategoryName("Income");
            category1.setCategoryDescription("Description");

            Category category2=new Category();
            category2.setCategoryName("Outcome");
            category2.setCategoryDescription("Description");

            Category category3=new Category();
            category3.setCategoryName("Result");
            category3.setCategoryDescription("Description");

            categoryDAO.insert(category1);
            categoryDAO.insert(category2);
            categoryDAO.insert(category3);

            Note note1 =new Note();
            note1.setNoteName("Monthly Income");
            note1.setUnitNominal("1000000");
            note1.setCategoryId(1);

            Note note2 =new Note();
            note2.setNoteName("Buy Shoes");
            note2.setUnitNominal("500000");
            note2.setCategoryId(2);

            Note note3 =new Note();
            note3.setNoteName("Total Income");
            note3.setUnitNominal("1000000");
            note3.setCategoryId(3);

            Note note4 =new Note();
            note4.setNoteName("Total Outcome");
            note4.setUnitNominal("510000");
            note4.setCategoryId(3);

            Note note5 =new Note();
            note5.setNoteName("Your Balance");
            note5.setUnitNominal("490000");
            note5.setCategoryId(3);

            noteDAO.insert(note1);
            noteDAO.insert(note2);
            noteDAO.insert(note3);
            noteDAO.insert(note4);
            noteDAO.insert(note5);


            return null;
        }
    }

}
