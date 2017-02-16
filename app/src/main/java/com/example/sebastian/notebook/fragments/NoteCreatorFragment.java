package com.example.sebastian.notebook.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sebastian.notebook.Note;
import com.example.sebastian.notebook.R;
import com.example.sebastian.notebook.activities.MainActivity;
import com.example.sebastian.notebook.activities.NoteCreator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteCreatorFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 12345;

    private String headerText;
    private String descriptionText;
    private ImageView notePhoto;
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
        notePhoto = (ImageView) view.findViewById(R.id.image_creator);
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
        realm.beginTransaction();
        realm.copyToRealm(note);
        realm.commitTransaction();
    }

    public void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        NoteCreatorFragment noteCreatorFragment = this;
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
                notePhoto.setImageBitmap(photo);
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
            case R.id.action_camera:
                takePhoto();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
