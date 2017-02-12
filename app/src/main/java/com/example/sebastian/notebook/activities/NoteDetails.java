package com.example.sebastian.notebook.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.sebastian.notebook.R;
import com.example.sebastian.notebook.fragments.NoteDetailsFragment;

public class NoteDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getIntent().getExtras().getLong("noteId");
        Log.e("noteDetails", " " + id);
        Bundle bundle = new Bundle();
        NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
        noteDetailsFragment.setNoteID((int) id);
        bundle.putLong("id", id);
        noteDetailsFragment.setArguments(bundle);
        setContentView(R.layout.activity_note_details);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return true;
    }
}
