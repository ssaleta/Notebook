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
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_creator, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                Intent intent = new Intent(getActivity(),NoteCreator.class);
                startActivity(intent);
                return true;
            case R.id.action_clear_notes:
                realm.beginTransaction();
                final RealmResults<Note> notesResult = realm.where(Note.class).findAll();
                notesResult.deleteAllFromRealm();
                realm.commitTransaction();
                Intent intent2 = new Intent(getActivity(),MainActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_camera:
                takePhoto();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /*public void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("NoteCreatorFragment","result");
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Do something with imagePath

                Bitmap photo = (Bitmap) data.getExtras().get("data");

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri selectedImage = getImageUri(getActivity(), photo);
                String realPath=getRealPathFromURI(selectedImage);
                selectedImage = Uri.parse(realPath);
            }
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }*/


}

