package com.example.sebastian.notebook.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import com.example.sebastian.notebook.NoteListListener;
import com.example.sebastian.notebook.R;
import com.example.sebastian.notebook.fragments.NoteDetailsFragment;
import com.example.sebastian.notebook.fragments.NotesListFragment;

public class MainActivity extends AppCompatActivity implements NoteListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View fragmentContainer = findViewById(R.id.fragment_container);

            NotesListFragment list = new NotesListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, list);
        /*    transaction.addToBackStack(null);*/

            transaction.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteCreator.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create) {
            Intent intent = new Intent(MainActivity.this, NoteCreator.class);
            startActivity(intent);

        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }



    @Override
    public void itemClicked(long id) {
        /*Log.e("MainActivity", "id" +id);
        Intent intent = new Intent(MainActivity.this, NoteDetails.class);
        intent.putExtra("noteId", id);
        startActivity(intent);*/
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null){
            NoteDetailsFragment details = new NoteDetailsFragment();
            details.setNoteID((int) id);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, details);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
}
