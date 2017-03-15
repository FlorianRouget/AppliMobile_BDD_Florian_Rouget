package com.example.florian.applimobile_bdd_florianrouget;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ListActivity{

    private final int RESULT_SELECTION = 0;

    private ChapitreBDD db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("debug","Main onCreate");

        Log.d("debug","objects initialized");

        //insertRecords();
        //displayContentProvider();

        db = new ChapitreBDD(this);
        Log.d("debug","calling openForWrite");
        db.openForWrite();
        Log.d("debug","DB opened in main");

        List<Chapitre> values = db.getAllChapters();
        ArrayAdapter<Chapitre> adapter = new ArrayAdapter<Chapitre>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Chapitre> adapter = (ArrayAdapter<Chapitre>) getListAdapter();
        Chapitre chapitre = null;
        switch (view.getId()) {
            case R.id.add_chapter:
                goToNextActivity();
                /*
                Chapitre chapitres = new Chapitre();
                chapitres.setChapterName("NewName");
                chapitres.setChapterDesc(EDIT_Chapter_Name.getText().toString());
                // save the new comment to the database
                chapitre = db.createChapitre(chapitres);
                adapter.add(chapitre);
                */
                break;
            case R.id.rm_chapter:
                if (getListAdapter().getCount() > 0) {
                    chapitre = (Chapitre) getListAdapter().getItem(0);
                    db.deleteChapitre(chapitre);
                    adapter.remove(chapitre);
                }
                break;
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        db.openForWrite();
        super.onResume();
    }

    @Override
    protected void onPause() {
        db.close();
        super.onPause();
    }

    private void goToNextActivity(){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivityForResult(intent, RESULT_SELECTION);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        db.openForWrite();
        ArrayAdapter<Chapitre> adapter = (ArrayAdapter<Chapitre>) getListAdapter();

        String firstP = data.getStringExtra("Nom");
        String secondP = data.getStringExtra("Desc");

        Chapitre chapitres = new Chapitre();
        chapitres.setChapterName(firstP);
        chapitres.setChapterDesc(secondP);
        db.createChapitre(chapitres);
        adapter.add(chapitres);
    }

    //used with content provider

    private void displayContentProvider() {
        Log.d("debug","enter displayContentProvider");
        String columns[] = new String[] { db.COL_ID, db.COL_NAME, db.COL_DESC };
        Log.d("debug","DCP - string[] created");
        Uri mContacts = db.SOURCE;                                                      // Crash here /!\
        Log.d("debug","DCP - URI affected to mContacts");
        Cursor cur = getContentResolver().query(mContacts, columns, null, null, null);
        Log.d("debug","DCP - cursor setted");
        Toast.makeText(this, cur.getCount() + "", Toast.LENGTH_LONG).show();
        Log.d("debug","DCP - Toasty !");

        if (cur.moveToFirst()) {
            String name = null;
            Log.d("debug","DCP - if cur.moveToFirst");
            do {
                Log.d("debug","DCP - dowhile loop beginning");
                name = cur.getString(cur.getColumnIndex(db.COL_ID)) + " " +
                        cur.getString(cur.getColumnIndex(db.COL_NAME)) + " " +
                        cur.getString(cur.getColumnIndex(db.COL_DESC));
                Toast.makeText(this, name + " ", Toast.LENGTH_LONG).show();
                Log.d("debug","DCP - dowhile loop ending");
            } while (cur.moveToNext());
        }
        Log.d("debug","exit displayContentProvider");
    }

    private void insertRecords() {
        Log.d("debug", "enter insertRecords");
        ContentValues contact = new ContentValues();
        contact.put(db.COL_NAME, "Android");
        contact.put(db.COL_DESC, "Introduction à la programmation sous Android");
        getContentResolver().insert(db.SOURCE, contact);
        Log.d("debug","step1");
        contact.clear();
        contact.put(db.COL_NAME, "Java");
        contact.put(db.COL_DESC, "Introduction à la programmation Java");
        getContentResolver().insert(db.SOURCE, contact);
        Log.d("debug","step2");
        contact.clear();
        contact.put(db.COL_NAME, "Iphone");
        contact.put(db.COL_DESC, "Introduction à l'objectif C");
        getContentResolver().insert(db.SOURCE, contact);
        Log.d("debug","exit insertRecords");
    }

}
