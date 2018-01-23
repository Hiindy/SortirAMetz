package com.example.cindy.sortirametz.Vue.BDD;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cindy.sortirametz.BDD.Site;
import com.example.cindy.sortirametz.R;
import com.example.cindy.sortirametz.Vue.Carte.ClientCarte;
import com.google.android.gms.maps.model.LatLng;

public class AjoutBDD extends AppCompatActivity {
    private LatLng positionCourrante;
    private EditText nom;
    private EditText latitude;
    private EditText longitude;
    private EditText adresse;
    private EditText categorie;
    private EditText resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_bdd);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            positionCourrante = new LatLng(extras.getDouble("positionLatitude"), extras.getDouble("positionLongitude"));
        }

        nom = (EditText) findViewById(R.id.ajoutNom);
        latitude = (EditText) findViewById(R.id.ajoutLatitude);
        longitude = (EditText) findViewById(R.id.ajoutLongitude);
        adresse = (EditText) findViewById(R.id.ajoutAdresse);
        categorie = (EditText) findViewById(R.id.ajoutCategorie);
        resume = (EditText) findViewById(R.id.ajoutResume);

        /* Checkbox */
        CheckBox okDonnees = (CheckBox) findViewById(R.id.okDonnees);
         /* Event sur la sélection ou non de la checkbox */
        okDonnees.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    latitude.setText(String.valueOf(positionCourrante.latitude));
                    latitude.setFocusable(false);
                    longitude.setText(String.valueOf(positionCourrante.longitude));
                    longitude.setFocusable(false);
                } else {
                    latitude.setFocusableInTouchMode(true);
                    longitude.setFocusableInTouchMode(true);
                }

            }
        });


        final Button buttonAjouter = findViewById(R.id.btn_ajouter);
        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double newLatitude;
                Double newLongitude;


                if (TextUtils.isEmpty(nom.getText())) {
                    nom.setError("Le nom est obligatoire");
                } else if (TextUtils.isEmpty(latitude.getText())) {
                    latitude.setError("La latitude est obligatoire");
                } else if (TextUtils.isEmpty(longitude.getText())) {
                    longitude.setError("La longitude est obligatoire");
                } else {
                    try {
                        newLatitude = Double.parseDouble(latitude.getText().toString());
                        newLongitude = Double.parseDouble(longitude.getText().toString());
                    } catch (final NumberFormatException e) {
                        newLatitude = 0.0;
                        newLongitude = 0.0;
                    }

                    Site site = new Site(nom.getText().toString(),
                            newLatitude,
                            newLongitude,
                            adresse.getText().toString(),
                            categorie.getText().toString(),
                            resume.getText().toString());
                    site.ajouterSite(getContentResolver(), site);
                    new AlertDialog.Builder(AjoutBDD.this).setTitle("").setMessage("Site ajouté!").setNeutralButton("Fermer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentConsultation = new Intent(AjoutBDD.this, ConsultationBDD.class);

                            AjoutBDD.this.startActivity(intentConsultation);
                        }
                    }).show();

                }

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
