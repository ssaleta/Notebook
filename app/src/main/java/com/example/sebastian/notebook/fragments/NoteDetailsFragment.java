package com.example.sebastian.notebook.fragments;


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


    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    private int noteID;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        headDetails = (TextView) view.findViewById(R.id.header_details);
        desctiptionDetails = (TextView) view.findViewById(R.id.description_details);
        realm.init(view.getContext());
        realm = Realm.getDefaultInstance();
        notesResult = realm.where(Note.class).findAll();
        int id = noteID;
        Log.e("NoteDetailsFragment"," "+id);
        ArrayList<Note> noteArrayList = new ArrayList(notesResult);
        Note note = noteArrayList.get(id);
        headDetails.setText(note.getHead());
        desctiptionDetails.setText(note.getContent());
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
                Intent intent = new Intent(getActivity(),NoteCreator.class);
                startActivity(intent);
                return true;
            case R.id.action_delete_note:
                realm.beginTransaction();
                final RealmResults<Note> notesResult = realm.where(Note.class).findAll();
                notesResult.deleteFromRealm(noteID);
                realm.commitTransaction();
                Intent intent2 = new Intent(getActivity(),MainActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
