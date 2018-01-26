package com.example.cindy.sortirametz.BDD;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Cindy on 26/01/2018.
 */

public class Categorie {

    // Colonnes de la base de données
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LIBELLE = "libelle";
    public static final String COLUMN_IMAGE = "image";

    public static String[] listeCategorie = {"Restaurant", "Bar", "Eglise", "Musée"};

    // Attributs
    private int id;
    private String libelle;
    private String image;


    public static Cursor getAllCategories(ContentResolver contentResolver){
        String[] projection = new String[] {COLUMN_ID,
                COLUMN_LIBELLE,
                COLUMN_IMAGE};
        return contentResolver.query(BDDSqlLiteHelper.CONTENT_URI, projection, null, null, null);
    }

    public void ajouterCategorie(ContentResolver content, Categorie categorie) {
       /* // On crée un ContentValues pour ajouter les différents attributs
        ContentValues values = new ContentValues();
        values.put(Site.COLUMN_NOM, "Temple Neuf");
        values.put(Site.COLUMN_LATITUDE, 15482);
        values.put(Site.COLUMN_LONGITUDE, 65);
        values.put(Site.COLUMN_ADRESSE, "blable");
        values.put(Site.COLUMN_CATEGORIE, "religieux");
        values.put(Site.COLUMN_RESUME, "ceci est un test");

        content.insert(BDDSqlLiteHelper.CONTENT_URI, values);
        values.clear();

        values.put(Site.COLUMN_NOM, "Cathédrale de Metz");
        values.put(Site.COLUMN_LATITUDE, 14.58);
        values.put(Site.COLUMN_LONGITUDE, 65.78);
        values.put(Site.COLUMN_ADRESSE, "deuxième");
        values.put(Site.COLUMN_CATEGORIE, "religieux");
        values.put(Site.COLUMN_RESUME, "ceci est un deuxième test");
        content.insert(BDDSqlLiteHelper.CONTENT_URI, values);*/
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
