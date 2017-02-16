package com.example.sebastian.notebook.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sebastian.notebook.Note;
import com.example.sebastian.notebook.R;
import com.example.sebastian.notebook.activities.MainActivity;
import com.example.sebastian.notebook.activities.NoteCreator;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment {
    private TextView headDetails;
    private TextView desctiptionDetails;
    private RealmResults<Note> notesResult;
    private Realm realm;
    private int noteId;

    public void setNoteID(int noteId) {
        this.noteId = noteId;
    }



    public NoteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        headDetails = (TextView) view.findViewById(R.id.header_details);
        desctiptionDetails = (TextView) view.findViewById(R.id.description_details);
        initRealm(view.getContext());
        getAllNotes();
        headDetails.setText(getSelectedNote().getHead());
        desctiptionDetails.setText(getSelectedNote().getContent());
    }

    private void initRealm(Context context){
        realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
    }
    private void getAllNotes(){
        notesResult = realm.where(Note.class).findAll();
    }
    private Note getSelectedNote(){
        ArrayList<Note> noteArrayList = new ArrayList(notesResult);
        Note note = noteArrayList.get(noteId);
        return note;
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                goToActivity(NoteCreator.class);
                return true;
            case R.id.action_delete_note:
                notesResult.deleteFromRealm(noteId);
                realm.commitTransaction();
                goToActivity(MainActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToActivity(Class goToActivityClass){
        Intent goToActivityIntent = new Intent(getActivity(), goToActivityClass);
        startActivity(goToActivityIntent);
    }
}
