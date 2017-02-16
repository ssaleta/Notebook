package com.example.sebastian.notebook.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ListFragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesListFragment extends ListFragment {
    private static final int REQUEST_IMAGE_CAPTURE = 12345;
    private Realm realm;
    private ArrayList<String> noteArrayList;
    private NoteListListener noteListListener;
    private RealmResults<Note> notesResult;
    private Note[] noteList;

    public NotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.noteListListener = (NoteListListener) context;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (noteListListener != null) {
            noteListListener.itemClicked(id);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        initRealm(container);
        getAllNotes();
        getNoteList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, noteArrayList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

    }
    private void getAllNotes(){
        notesResult = realm.where(Note.class).findAll();
    }

    private ArrayList<String> getNoteList(){
        if (notesResult.size() != 0) {
            noteList = new Note[notesResult.size()];
            for (int i = 0; i < notesResult.size(); i++) {
                noteList[i] = notesResult.get(i);
            }
            noteArrayList = new ArrayList<String>();
            for (int i = 0; i < noteList.length; i++) {
                noteArrayList.add(noteList[i].getHead());
            }
        }
        return noteArrayList;
    }

    public void initRealm(ViewGroup container){
        realm.init(container.getContext());
        realm = Realm.getDefaultInstance();
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_creator, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
