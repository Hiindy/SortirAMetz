package com.example.cindy.sortirametz.BDD;

import android.provider.BaseColumns;

public class Site implements BaseColumns{

    private Site(){}

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ADRESSE = "adresse";
    public static final String COLUMN_CATEGORIE = "categorie";
    public static final String COLUMN_RESUME = "resume";
}