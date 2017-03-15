package com.example.florian.applimobile_bdd_florianrouget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Created by florian on 15/03/2017.
 */

public class ChapitreBaseSQLite extends SQLiteOpenHelper {

    //Le nom de la table ainsi que les noms des colonnes sont de type public static final String
    public static int VERSION = 1;
    public static String BDD_NAME = "Chapters.db";
    public static final String nom_Table = "Chapitre";
    public static final String Ma_Colonne_Id = "Chap_Id";
    public static final String Ma_Colonne_Nom = "Chap_Nom";
    public static final String Ma_Colonne_Desc = "Chap_Desc";
    //La requête de création de la table (cf. cours de DDL) est aussi une chaîne de type public static final String
    public static final String CREATE_BDD = " CREATE TABLE `" + nom_Table + "` (`" + Ma_Colonne_Id + "` INTEGER PRIMARY KEY AUTOINCREMENT,  `" + Ma_Colonne_Nom +"` text NOt NULL, `"+ Ma_Colonne_Desc + "` text NOT NULL);";

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.florian.applimobile_bdd_florianrouget.ContentProviderExtender");

    //Le constructeur de votre classe
    /*public ChapitreBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }*/
    public ChapitreBaseSQLite(Context context){
        super(context, BDD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("debug","SQLiteOpenHelper const");
        db.execSQL(CREATE_BDD);
        Log.d("debug","OpenHelper SQL executed");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS `"+nom_Table+"` ;");
        onCreate(db);
    }

}
