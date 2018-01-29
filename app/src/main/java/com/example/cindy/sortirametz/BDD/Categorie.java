package com.example.cindy.sortirametz.BDD;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.cindy.sortirametz.R;

import java.util.ArrayList;

/**
 * Created by Cindy on 26/01/2018.
 */

public class Categorie {

    // Colonnes de la base de données
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LIBELLE = "libelle";
    public static final String COLUMN_IMAGE = "image";
    public static ArrayList<Categorie> listeCategories;

    // Attributs
    private int id;
    private String libelle;
    private int image;

    public Categorie() {
        listeCategories = new ArrayList();
        listeCategories.add(new Categorie("Restaurant", R.drawable.restaurant));
        listeCategories.add(new Categorie("Bar", R.drawable.bar));
        listeCategories.add(new Categorie("Eglise", R.drawable.eglise));
        listeCategories.add(new Categorie("Musée", R.drawable.musee));
    }

    public Categorie(String libelle, int image) {
        this.libelle = libelle;
        this.image = image;
    }

    public static Cursor getAllCategories(ContentResolver contentResolver){
        String[] projection = new String[] {COLUMN_ID,
                COLUMN_LIBELLE,
                COLUMN_IMAGE};
        return contentResolver.query(BDDSqlLiteHelper.CONTENT_URI, projection, null, null, null);
    }

    public void ajouterCategorie(ContentResolver content, Categorie categorie) {
        ContentValues values = this.setContentValues(categorie);

        content.insert(BDDSqlLiteHelper.CONTENT_URI, values);
        values.clear();
    }

    public ContentValues setContentValues(Categorie categorie) {
        ContentValues values = new ContentValues();
        values.put(this.COLUMN_LIBELLE, categorie.getLibelle());
        values.put(this.COLUMN_IMAGE, categorie.getImage());

        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static ArrayList<Categorie> getListeCategories() {
        return listeCategories;
    }

    public static void setListeCategories(ArrayList<Categorie> listeCategories) {
        Categorie.listeCategories = listeCategories;
    }

}
