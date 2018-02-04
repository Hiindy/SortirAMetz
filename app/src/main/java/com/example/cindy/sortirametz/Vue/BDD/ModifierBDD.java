package com.example.cindy.sortirametz.Vue.BDD;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cindy.sortirametz.BDD.Categorie;
import com.example.cindy.sortirametz.BDD.Site;
import com.example.cindy.sortirametz.R;
import com.example.cindy.sortirametz.Vue.BDD.SpinnerCategorie.SpinnerAdapter;
import com.google.android.gms.maps.model.LatLng;

public class ModifierBDD extends AppCompatActivity {

    private Site site;
    private EditText nom;
    private EditText latitude;
    private EditText longitude;
    private EditText adresse;
    private Spinner categorie;
    private EditText resume;
    private LatLng positionCourrante;

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
            // On recherche la catégorie
            for(int i=0; i<new Categorie().getListeCategories().size();i++){
                if(Categorie.listeCategories.get(i).getLibelle().equals(extras.getString("categorie")))
                {
                    site.setCategorie(Categorie.listeCategories.get(i));
                }
            }
            site.setResume(extras.getString("resume"));
            positionCourrante = new LatLng(extras.getDouble("positionLatitude"), extras.getDouble("positionLongitude"));
        }

        nom = (EditText) findViewById(R.id.modifNom);
        latitude = (EditText) findViewById(R.id.modifLatitude);
        longitude = (EditText) findViewById(R.id.modifLongitude);
        adresse = (EditText) findViewById(R.id.modifAdresse);
        categorie = (Spinner) findViewById(R.id.modifCategorie);
        resume = (EditText) findViewById(R.id.modifResume);

        /* Spinner */
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_categorie,R.id.txt,new Categorie().getListeCategories());
        categorie.setAdapter(adapter);

        nom.setText(site.getNom());
        latitude.setText(String.valueOf(site.getLatitude()));
        longitude.setText(String.valueOf(site.getLongitude()));
        adresse.setText(site.getAdresse());
        categorie.setSelection(adapter.getPosition(site.getCategorie()));
        resume.setText(site.getResume());

        /* Checkbox */
        CheckBox okDonnees = (CheckBox) findViewById(R.id.okDonneesModif);
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

        final Button buttonModifier = findViewById(R.id.btn_modifier);
        buttonModifier.setOnClickListener(new View.OnClickListener() {
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
                    // On met à jour le site
                    site.setNom(nom.getText().toString());
                    site.setLatitude(newLatitude);
                    site.setLongitude(newLongitude);
                    site.setAdresse(adresse.getText().toString());
                    site.setCategorie((Categorie) categorie.getSelectedItem());
                    site.setResume(resume.getText().toString());

                    site.modifierSite(getContentResolver(), site);

                    new AlertDialog.Builder(ModifierBDD.this).setTitle("").setMessage("Site modifié!").setNeutralButton("Fermer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentConsultation = new Intent(ModifierBDD.this, ConsultationBDD.class);
                            intentConsultation.putExtra("positionLatitude", positionCourrante.latitude);
                            intentConsultation.putExtra("positionLongitude", positionCourrante.longitude);
                            ModifierBDD.this.startActivity(intentConsultation);
                        }
                    }).show();

                }
            }
        });

        final Button buttonSupprimer = findViewById(R.id.btn_supprimer);
        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                site.supprimerSite(getContentResolver(), site);
                                new AlertDialog.Builder(ModifierBDD.this).setTitle("").setMessage("Site supprimé !").setNeutralButton("Fermer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intentConsultation = new Intent(ModifierBDD.this, ConsultationBDD.class);
                                        intentConsultation.putExtra("positionLatitude", positionCourrante.latitude);
                                        intentConsultation.putExtra("positionLongitude", positionCourrante.longitude);

                                        ModifierBDD.this.startActivity(intentConsultation);
                                    }
                                }).show();

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifierBDD.this);
                builder.setMessage("Voulez-vous vraiment supprimer le site " + site.getNom() + " ?").setPositiveButton("Oui", dialogClickListener)
                        .setNegativeButton("Non", dialogClickListener).show();
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