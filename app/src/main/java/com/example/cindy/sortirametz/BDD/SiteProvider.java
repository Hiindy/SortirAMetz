package com.example.cindy.sortirametz.BDD; /**
 * Created by Cindy on 19/01/2018.
 */


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class SiteProvider extends ContentProvider{

    private SiteDatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {

        dbHelper = new SiteDatabaseHelper(getContext());
        return true;
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            if (id < 0)
                return db.delete(
                        SiteDatabaseHelper.TABLE_NAME,
                        selection, selectionArgs);
            else
                return db.delete(
                        SiteDatabaseHelper.TABLE_NAME,
                        Site.COLUMN_ID + "=" + id, selectionArgs);
        } finally {
            db.close();
        }
    }

    @Override
    public String getType(Uri arg0) {
        return SiteDatabaseHelper.MIME;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            long id = db.insertOrThrow(          SiteDatabaseHelper.TABLE_NAME, null, values);

            if (id == -1) {
                throw new RuntimeException(String.format(
                        "%s : Failed to insert [%s] for unknown reasons.","PolytechProvider", values, uri));
            } else {
                return ContentUris.withAppendedId(uri, id);
            }

        } finally {
            db.close();
        }
    }

    private long getId(Uri uri) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null) {
            try {
                return Long.parseLong(lastPathSegment);
            } catch (NumberFormatException e) {
                Log.e("PolytechProvider", "Number Format Exception : " + e);
            }
        }
        return -1;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (id < 0) {
            return  db.query(SiteDatabaseHelper.TABLE_NAME,
                    projection, selection, selectionArgs, null, null,
                    sortOrder);
        } else {
            return      db.query(SiteDatabaseHelper.TABLE_NAME,
                    projection, Site.COLUMN_ID+ "=" + id, null, null, null,
                    null);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            if (id < 0)
                return db.update( SiteDatabaseHelper.TABLE_NAME,values, selection, selectionArgs);
            else
                return db.update(                               SiteDatabaseHelper.TABLE_NAME,
                        values, Site.COLUMN_ID + "=" + id, null);
        } finally {
            db.close();
        }

    }

}