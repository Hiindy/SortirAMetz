package com.example.cindy.sortirametz.Vue.Carte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.cindy.sortirametz.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Cindy on 26/01/2018.
 */

public class InfoWindowSite implements GoogleMap.InfoWindowAdapter {
    private Context context;
    LayoutInflater inflater;

    public InfoWindowSite(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vueSiteInfoContents = inflater.inflate(R.layout.site_info_contents, null);

        // On récupère le snippet = La liste des données à afficher
        String snippet = marker.getSnippet();

        // On utilise la fonction split avec le paramètre '/' pour obtenir les différentes informations
        String[] infos = snippet.split("/");

        // On associe le TextView aux différents éléments de infos
        TextView titre = (TextView) vueSiteInfoContents.findViewById(R.id.info_site_nom);
        titre.setText(marker.getTitle());

        TextView adresse = (TextView) vueSiteInfoContents.findViewById(R.id.info_site_adresse);
        adresse.setText(infos[0]);

        TextView resume = (TextView) vueSiteInfoContents.findViewById(R.id.info_site_resume);
        resume.setText(infos[1]);

        TextView coordonnees = (TextView) vueSiteInfoContents.findViewById(R.id.info_site_coordonnees);
        coordonnees.setText(infos[2]);


        return vueSiteInfoContents;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
