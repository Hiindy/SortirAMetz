package com.example.cindy.sortirametz.Vue.BDD;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.cindy.sortirametz.BDD.BDDSqlLiteHelper;
import com.example.cindy.sortirametz.BDD.Site;
import com.example.cindy.sortirametz.R;
import com.example.cindy.sortirametz.Vue.Carte.ClientCarte;
import com.google.android.gms.maps.model.LatLng;

public class ConsultationBDD extends AppCompatActivity {
    private ListView listeSite;
    private LatLng positionCourrante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_bdd);
        displayContentProvider();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            positionCourrante = new LatLng(extras.getDouble("positionLatitude"), extras.getDouble("positionLongitude"));
        }
    }


    private void displayContentProvider() {
        String[] columns = {Site.COLUMN_ID, Site.COLUMN_NOM, Site.COLUMN_LATITUDE,
                Site.COLUMN_LONGITUDE, Site.COLUMN_ADRESSE, Site.COLUMN_CATEGORIE, Site.COLUMN_RESUME};

        int[] textView = {R.id.id, R.id.nom, R.id.latitude, R.id.longitude, R.id.adresse, R.id.categorie, R.id.resume};
        Uri mContacts = BDDSqlLiteHelper.CONTENT_URI;
        Cursor cur = Site.getAllSite(getContentResolver());

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.layout_site_info,
                cur,
                columns,
                textView,
                0
        );

        ListView listView = (ListView) findViewById(R.id.listViewSite);
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                Intent intent = new Intent(ConsultationBDD.this, ModifierBDD.class);
                intent.putExtra("id", cursor.getString(cursor.getColumnIndex(Site.COLUMN_ID)));
                intent.putExtra("latitude", cursor.getString(cursor.getColumnIndex(Site.COLUMN_LATITUDE)));
                intent.putExtra("longitude", cursor.getString(cursor.getColumnIndex(Site.COLUMN_LONGITUDE)));
                intent.putExtra("adresse", cursor.getString(cursor.getColumnIndex(Site.COLUMN_ADRESSE)));
                intent.putExtra("categorie", cursor.getString(cursor.getColumnIndex(Site.COLUMN_CATEGORIE)));
                intent.putExtra("resume", cursor.getString(cursor.getColumnIndex(Site.COLUMN_RESUME)));
                intent.putExtra("nom", cursor.getString(cursor.getColumnIndex(Site.COLUMN_NOM)));
                intent.putExtra("positionLatitude", positionCourrante.latitude);
                intent.putExtra("positionLongitude", positionCourrante.longitude);
                ConsultationBDD.this.startActivity(intent);
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
                Intent intentConsultation = new Intent(ConsultationBDD.this, ConsultationBDD.class);
                intentConsultation.putExtra("positionLatitude", this.positionCourrante.latitude);
                intentConsultation.putExtra("positionLongitude", this.positionCourrante.longitude);
                ConsultationBDD.this.startActivity(intentConsultation);
                return true;
            case R.id.ajouter:
            case R.id.ajout:
                Intent intentAjout = new Intent(ConsultationBDD.this, AjoutBDD.class);
                intentAjout.putExtra("positionLatitude", this.positionCourrante.latitude);
                intentAjout.putExtra("positionLongitude", this.positionCourrante.longitude);
                ConsultationBDD.this.startActivity(intentAjout);
                return true;

            case R.id.map:
                Intent intentCarte = new Intent(ConsultationBDD.this, ClientCarte.class);
                ConsultationBDD.this.startActivity(intentCarte);
                    return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
