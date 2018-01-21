package com.example.cindy.sortirametz.Vue.BDD;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cindy.sortirametz.BDD.Site;
import com.example.cindy.sortirametz.R;

public class AjoutBDD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_bdd);

        final Button buttonAjouter = findViewById(R.id.btn_ajouter);
        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nom = (EditText) findViewById(R.id.ajoutNom);
                EditText latitude = (EditText) findViewById(R.id.ajoutLatitude);
                EditText longitude = (EditText) findViewById(R.id.ajoutLongitude);
                EditText adresse = (EditText) findViewById(R.id.ajoutAdresse);
                EditText categorie = (EditText) findViewById(R.id.ajoutCategorie);
                EditText resume = (EditText) findViewById(R.id.ajoutResume);
                Site site = new Site(nom.getText().toString(),
                        latitude.getText().toString(),
                        longitude.getText().toString(),
                        adresse.getText().toString(),
                        categorie.getText().toString(),
                        resume.getText().toString());
                site.ajouterSite(getContentResolver(), site);
                Intent intentConsultation = new Intent(AjoutBDD.this, ConsultationBDD.class);
                AjoutBDD.this.startActivity(intentConsultation);
            }
        });
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
                Intent intentConsultation = new Intent(AjoutBDD.this, ConsultationBDD.class);
                AjoutBDD.this.startActivity(intentConsultation);
                return true;
            case R.id.ajout:
                Intent intentAjout = new Intent(AjoutBDD.this, AjoutBDD.class);
                AjoutBDD.this.startActivity(intentAjout);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
