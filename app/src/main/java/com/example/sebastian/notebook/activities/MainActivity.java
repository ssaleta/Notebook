package com.example.sebastian.notebook.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.example.sebastian.notebook.NoteListListener;
import com.example.sebastian.notebook.R;
import com.example.sebastian.notebook.fragments.NoteDetailsFragment;
import com.example.sebastian.notebook.fragments.NotesListFragment;

public class MainActivity extends AppCompatActivity implements NoteListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        addNoteListFragment();
        addFloatingActionButton();
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void addNoteListFragment(){
        NotesListFragment notesListFragment = new NotesListFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, notesListFragment);
        fragmentTransaction.commit();
    }
    public void addFloatingActionButton(){
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noteCreatorIntent = new Intent(MainActivity.this, NoteCreator.class);
                startActivity(noteCreatorIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("NoteCreator", "photo");
    }

    @Override
    public void itemClicked(long id) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null){
            NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
            noteDetailsFragment.setNoteID((int) id);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, noteDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
}
