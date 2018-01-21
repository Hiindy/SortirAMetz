package com.example.cindy.sortirametz.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Cindy on 19/01/2018.
 */

public class SiteDatabaseHelper extends SQLiteOpenHelper {

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.cindy.basededonnees.BDD.SiteProvider");

    public static final String DATABASE_NAME = "site.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "site";

    public static final String MIME = "vnd.android.cursor.item/vnd.com.example.cindy.basededonnees.BDD.site";

    SiteDatabaseHelper(Context context){
        super(context, SiteDatabaseHelper.DATABASE_NAME, null,SiteDatabaseHelper.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " +
                SiteDatabaseHelper.TABLE_NAME + " ("
                + Site.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Site.COLUMN_NOM + " TEXT NOT NULL, "
                + Site.COLUMN_LATITUDE + " DOUBLE NOT NULL, "
                + Site.COLUMN_LONGITUDE + " DOUBLE NOT NULL, "
                + Site.COLUMN_ADRESSE + " TEXT, "
                + Site.COLUMN_CATEGORIE + " TEXT, "
                + Site.COLUMN_RESUME + " TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int ancienne_version, int nouvelle_version) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SiteDatabaseHelper.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
