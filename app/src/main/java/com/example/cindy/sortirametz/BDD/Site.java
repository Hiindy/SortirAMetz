package com.example.cindy.sortirametz.BDD;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.provider.BaseColumns;

public class Site implements BaseColumns{

    // Colonnes de la base de données
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ADRESSE = "adresse";
    public static final String COLUMN_CATEGORIE = "categorie";
    public static final String COLUMN_RESUME = "resume";


    // Attributs
    private int id;
    private String nom;
    private double latitude;
    private double longitude;
    private String adresse;
    private String categorie;
    private String resume;

    public Site(){}

    public Site(String nom, double latitude, double longitude) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Site(String nom, double latitude, double longitude, String adresse, String categorie, String resume) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adresse = adresse;
        this.categorie = categorie;
        this.resume = resume;
    }

    public  void ajouterSite(ContentResolver content, Site site) {
       /* // On crée un ContentValues pour ajouter les différents attributs
        ContentValues values = new ContentValues();
        values.put(Site.COLUMN_NOM, "Temple Neuf");
        values.put(Site.COLUMN_LATITUDE, 15482);
        values.put(Site.COLUMN_LONGITUDE, 65);
        values.put(Site.COLUMN_ADRESSE, "blable");
        values.put(Site.COLUMN_CATEGORIE, "religieux");
        values.put(Site.COLUMN_RESUME, "ceci est un test");

        content.insert(SiteDatabaseHelper.CONTENT_URI, values);
        values.clear();

        values.put(Site.COLUMN_NOM, "Cathédrale de Metz");
        values.put(Site.COLUMN_LATITUDE, 14.58);
        values.put(Site.COLUMN_LONGITUDE, 65.78);
        values.put(Site.COLUMN_ADRESSE, "deuxième");
        values.put(Site.COLUMN_CATEGORIE, "religieux");
        values.put(Site.COLUMN_RESUME, "ceci est un deuxième test");
        content.insert(SiteDatabaseHelper.CONTENT_URI, values);*/
        ContentValues values = new ContentValues();
        values.put(this.COLUMN_NOM, site.getNom());
        values.put(this.COLUMN_LATITUDE, site.getLatitude());
        values.put(this.COLUMN_LONGITUDE, site.getLongitude());
        values.put(this.COLUMN_ADRESSE, site.getAdresse());
        values.put(this.COLUMN_CATEGORIE, site.getCategorie());
        values.put(this.COLUMN_RESUME, site.getResume());

        content.insert(SiteDatabaseHelper.CONTENT_URI, values);
        values.clear();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}