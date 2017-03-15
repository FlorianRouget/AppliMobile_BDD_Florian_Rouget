package com.example.florian.applimobile_bdd_florianrouget;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by florian on 15/03/2017.
 */

public class ContentProviderExtender extends ContentProvider {

    private ChapitreBaseSQLite dbHelper;

    @Override
    public boolean onCreate(){
        dbHelper = new ChapitreBaseSQLite(getContext());
        return true;
    }

    private final String CONTENT_PROVIDER_MEME = "vnd.android.cursor.item/vnd.eni.example.florian.applimobile_bdd_florianrouget.Chapitre";

    public String getType(Uri uri){
        return CONTENT_PROVIDER_MEME;
    }

    public long getId(Uri contentUri){
        String lastPathSegment = contentUri.getLastPathSegment();
        if(lastPathSegment == null){
            Long.parseLong(lastPathSegment);
        }
        return -1;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            long id = db.insertOrThrow(dbHelper.nom_Table, null, values);
            if(id == -1){
                throw new RuntimeException(String.format(
                        "%s : Failed to insert [%s] for unknown reasons.","TutosAndroidProvider", values, uri));
            } else {
                return ContentUris.withAppendedId(uri, id);
            }
        } finally {
            db.close();
        }
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            if (id < 0){
                return db.update(dbHelper.nom_Table, values, selection, selectionArgs);
            }else{
                return db.update(dbHelper.nom_Table, values, dbHelper.Ma_Colonne_Id + "=" + id, null);
            }
        }finally {
            db.close();
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            if (id < 0)
                return db.delete(dbHelper.nom_Table, selection, selectionArgs);
            else
                return db.delete(dbHelper.nom_Table, dbHelper.Ma_Colonne_Id + "=" + id, selectionArgs);
        } finally {
            db.close();
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (id < 0) {
            return db.query(dbHelper.nom_Table, projection, selection, selectionArgs, null, null, sortOrder);
        } else {
            return db.query(dbHelper.nom_Table, projection, dbHelper.Ma_Colonne_Id + "=" + id, null, null, null, null);
        }
    }



}
