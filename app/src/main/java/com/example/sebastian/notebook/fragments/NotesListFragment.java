package com.example.sebastian.notebook.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sebastian.notebook.Note;
import com.example.sebastian.notebook.NoteListListener;
import com.example.sebastian.notebook.R;
import com.example.sebastian.notebook.activities.MainActivity;
import com.example.sebastian.notebook.activities.NoteCreator;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesListFragment extends ListFragment {

    private Realm realm;
    private ArrayList<String> noteArrayList;

    private NoteListListener noteListListener;

    public NotesListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.noteListListener = (NoteListListener)context;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        if(noteListListener != null){
            noteListListener.itemClicked(id);
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        realm.init(container.getContext());
        realm = Realm.getDefaultInstance();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                Intent intent = new Intent(getActivity(),NoteCreator.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

