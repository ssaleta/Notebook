package com.example.sebastian.notebook.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sebastian.notebook.R;

public class NoteCreator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_creator, menu);
        return true;
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
       Log.e("note creator","on destroy");
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       Log.e("NoteCreator","photo");
    }
}
