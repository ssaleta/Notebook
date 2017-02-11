package com.example.sebastian.notebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteCreatorFragment extends Fragment {
    private String headerText;
    private String descriptionText;
    private EditText header;
    private EditText description;
    private Note note;
    private ArrayList<Note> noteArrayList = new ArrayList<Note>();
    private Realm realm;
    private RealmResults<Note> notesResult;

    public NoteCreatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_note_creator, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        header = (EditText) view.findViewById(R.id.head_creator);
        description = (EditText) view.findViewById(R.id.description_creator);
        initRealm();
    }

    public void initRealm(){
        realm.init(getContext());
        realm = Realm.getDefaultInstance();
    }

    public void createNote() {
        notesResult = realm.where(Note.class).findAll();
        headerText = header.getText().toString();
        descriptionText = description.getText().toString();
        note = new Note();
        note.setId(notesResult.size() + 1);
        note.setHead(headerText);
        note.setContent(descriptionText);
    }

    public void saveNote() {
        realm.copyToRealm(note);
        realm.commitTransaction();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                createNote();
                saveNote();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
