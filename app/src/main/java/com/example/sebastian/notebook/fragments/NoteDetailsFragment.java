package com.example.sebastian.notebook.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sebastian.notebook.Note;
import com.example.sebastian.notebook.R;

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

        headDetails = (TextView) view.findViewById(R.id.header_details);
        desctiptionDetails = (TextView) view.findViewById(R.id.description_details);
        realm.init(getContext());
        realm = Realm.getDefaultInstance();
        notesResult = realm.where(Note.class).findAll();
        int id = noteID;
        Log.e("NoteDetailsFragment"," "+id);
        ArrayList<Note> noteArrayList = new ArrayList(notesResult);
        Note note = noteArrayList.get(id);
        headDetails.setText(note.getHead());
    }
}
