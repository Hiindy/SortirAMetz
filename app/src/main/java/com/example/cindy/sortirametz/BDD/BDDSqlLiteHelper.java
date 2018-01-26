package com.example.cindy.sortirametz.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Cindy on 19/01/2018.
 *
 * https://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 */

public class BDDSqlLiteHelper extends SQLiteOpenHelper {

    // Uri
    public static final Uri CONTENT_URI = Uri.parse("content://com.example.cindy.basededonnees.BDD.SiteProvider");

    // Nom de la base de données
    public static final String DATABASE_NAME = "site.db";

    // Version de la base de données
    public static final int DATABASE_VERSION = 1;

    // Nom des tables
    public static final String TABLE_NAME_SITE = "site";
    public static final String TABLE_NAME_CATEGORIE = "categorie";

    // MIME
    public static final String MIME_SITE = "vnd.android.cursor.item/vnd.com.example.cindy.basededonnees.BDD.site";
    public static final String MIME_CATEGORIE = "vnd.android.cursor.item/vnd.com.example.cindy.basededonnees.BDD.categorie";

    // CONSTRUCTEUR
    BDDSqlLiteHelper(Context context) {
        super(context, BDDSqlLiteHelper.DATABASE_NAME, null, BDDSqlLiteHelper.DATABASE_VERSION);
    }


    // On crée la BdD
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Création de la table Catégorie
        sqLiteDatabase.execSQL("CREATE TABLE " +
                BDDSqlLiteHelper.TABLE_NAME_CATEGORIE + " ("
                + Categorie.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Categorie.COLUMN_LIBELLE + " TEXT NOT NULL, "
                + Categorie.COLUMN_IMAGE + " TEXT);");

        // Création de la table Site
        sqLiteDatabase.execSQL("CREATE TABLE " +
                BDDSqlLiteHelper.TABLE_NAME_SITE + " ("
                + Site.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Site.COLUMN_NOM + " TEXT NOT NULL, "
                + Site.COLUMN_LATITUDE + " DOUBLE NOT NULL, "
                + Site.COLUMN_LONGITUDE + " DOUBLE NOT NULL, "
                + Site.COLUMN_ADRESSE + " TEXT, "
                + Site.COLUMN_CATEGORIE + " TEXT NOT NULL, "
                + Site.COLUMN_RESUME + " TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int ancienne_version, int nouvelle_version) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BDDSqlLiteHelper.TABLE_NAME_SITE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BDDSqlLiteHelper.TABLE_NAME_CATEGORIE);
        onCreate(sqLiteDatabase);
    }

}
