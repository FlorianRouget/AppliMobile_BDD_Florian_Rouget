package com.example.florian.applimobile_bdd_florianrouget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 15/03/2017.
 */

public class ChapitreBDD {

    Uri SOURCE;
    String NOM_TABLE;
    String COL_ID;
    String COL_NAME;
    String COL_DESC;

    private SQLiteDatabase bdd;
    private ChapitreBaseSQLite chapitres;
    private String[] allColumns = { ChapitreBaseSQLite.Ma_Colonne_Id, ChapitreBaseSQLite.Ma_Colonne_Nom, ChapitreBaseSQLite.Ma_Colonne_Desc };

    public ChapitreBDD(Context context){
        Log.d("debug","Chapitre BDD constructor");
        chapitres = new ChapitreBaseSQLite(context);
        NOM_TABLE = chapitres.nom_Table;
        COL_ID = chapitres.Ma_Colonne_Id;
        COL_NAME = chapitres.Ma_Colonne_Nom;
        COL_DESC = chapitres.Ma_Colonne_Desc;
        SOURCE = chapitres.CONTENT_URI;
    }

    public void openForWrite() throws SQLException {
        Log.d("debug","enter openForWrite");
        bdd = chapitres.getWritableDatabase();
        Log.d("debug","DB opened");
    }

    public void openForRead(){
        bdd = chapitres.getReadableDatabase();
    }

    public void close(){
        bdd.close();
        Log.d("debug","DB closed");
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public ChapitreBaseSQLite getChapitres(){
        return chapitres;
    }

    public Chapitre createChapitre(Chapitre chapitre) {
        Log.d("debug","enter createChapter");
        ContentValues content = new ContentValues();
        content.put(COL_NAME, chapitre.getChapterName());
        content.put(COL_DESC, chapitre.getChapterDesc());
        long insertId = bdd.insertOrThrow(NOM_TABLE, null, content);
        Cursor cursor = bdd.query(NOM_TABLE, allColumns, COL_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Chapitre newChapter = cursorToChapter(cursor);
        cursor.close();
        Log.d("debug","exit createChapter");
        return newChapter;
    }

    public void deleteChapitre(Chapitre chapitre) {
        long id = chapitre.getChapterId();
        System.out.println("Comment deleted with id: " + id);
        bdd.delete(NOM_TABLE, COL_ID + " = " + id, null);
    }

    public List<Chapitre> getAllChapters() {
        Log.d("debug","enter getAllChapters");
        List<Chapitre> chapters = new ArrayList<Chapitre>();
        Log.d("debug","step 1");

        Cursor cursor = bdd.query(NOM_TABLE, allColumns, null, null, null, null, null);
        Log.d("debug","step 2");

        cursor.moveToFirst();
        Log.d("debug","step 3");

        while (!cursor.isAfterLast()) {
            Chapitre chapitre = cursorToChapter(cursor);
            chapters.add(chapitre);
            cursor.moveToNext();
        }
        Log.d("debug","step 4");

        // make sure to close the cursor
        cursor.close();
        Log.d("debug","exit getAllChapters");
        return chapters;
    }

    private Chapitre cursorToChapter(Cursor cursor) {
        Log.d("debug","enter cursorToChapter");
        Chapitre chapitre = new Chapitre();
        chapitre.setChapterId((int) cursor.getLong(0));
        chapitre.setChapterName(cursor.getString(1));
        chapitre.setChapterDesc(cursor.getString(2));
        Log.d("debug","exit cursorToChapter");
        return chapitre;
    }

}
