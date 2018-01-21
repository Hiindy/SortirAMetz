package com.example.cindy.sortirametz.Vue.BDD;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cindy.sortirametz.BDD.Site;
import com.example.cindy.sortirametz.R;

public class ModifierBDD extends AppCompatActivity {

    private Site site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_bdd);

        site = new Site();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            site.setId(Integer.parseInt(extras.getString("id")));
            site.setNom(extras.getString("nom"));
            site.setLatitude(Double.parseDouble(extras.getString("latitude")));
            site.setLongitude(Double.parseDouble(extras.getString("longitude")));
            site.setAdresse(extras.getString("adresse"));
            site.setCategorie(extras.getString("categorie"));
            site.setResume(extras.getString("resume"));
        }

        EditText nom = (EditText) findViewById(R.id.modifNom);
        EditText latitude = (EditText) findViewById(R.id.modifLatitude);
        EditText longitude = (EditText) findViewById(R.id.modifLongitude);
        EditText adresse = (EditText) findViewById(R.id.modifAdresse);
        EditText categorie = (EditText) findViewById(R.id.modifCategorie);
        EditText resume = (EditText) findViewById(R.id.modifResume);

        nom.setText(site.getNom());
        latitude.setText(String.valueOf(site.getLatitude()));
        longitude.setText(String.valueOf(site.getLongitude()));
        adresse.setText(site.getAdresse());
        categorie.setText(site.getAdresse());
        resume.setText(site.getResume());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bdd, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.consultation:
                Intent intentConsultation = new Intent(ModifierBDD.this, ConsultationBDD.class);
                ModifierBDD.this.startActivity(intentConsultation);
                return true;
            case R.id.ajout:
                Intent intentAjout = new Intent(ModifierBDD.this, ModifierBDD.class);
                ModifierBDD.this.startActivity(intentAjout);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
