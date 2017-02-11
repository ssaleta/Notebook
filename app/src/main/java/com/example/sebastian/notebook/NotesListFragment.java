package com.example.sebastian.notebook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesListFragment extends ListFragment {

    private Realm realm;
    private ArrayList<String> noteArrayList;

    public NotesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        realm.init(getContext());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<Note> notesResult = realm.where(Note.class).findAll();
        Log.e("realmresult", " " + notesResult.size());
        if (notesResult.size() != 0) {
            final Note[] noteList = new Note[notesResult.size()];
            for (int i = 0; i < notesResult.size(); i++) {
                noteList[i] = notesResult.get(i);
            }
            noteArrayList = new ArrayList<String>();
            for(int i=0; i<noteList.length;i++) {
                noteArrayList.add(noteList[i].getHead());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, noteArrayList);
            setListAdapter(adapter);
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void getNoteResult() {
        final RealmResults<Note> notesResult = realm.where(Note.class).findAll();
        Log.e("realmresult", " " + notesResult.size());
        if (notesResult.size() != 0) {
            final Note[] noteList = new Note[notesResult.size()];
            for (int i = 0; i < notesResult.size(); i++) {
                noteList[i] = notesResult.get(i);
            }
            String[] noteHead = new String[1];
            noteHead[0] = noteList[0].getHead();
            Log.e("realmresult", " " + noteList[0].getHead());
        }
    }
}

