package com.example.cindy.sortirametz.Vue.BDD;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.cindy.sortirametz.BDD.Site;
import com.example.cindy.sortirametz.BDD.SiteDatabaseHelper;
import com.example.cindy.sortirametz.R;
import com.example.cindy.sortirametz.Vue.BDD.AjoutBDD;

public class ConsultationBDD extends AppCompatActivity {
    ListView listeSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_bdd);
        displayContentProvider();
    }


    private void displayContentProvider() {
        String[] columns = {Site.COLUMN_ID, Site.COLUMN_NOM, Site.COLUMN_LATITUDE,
                Site.COLUMN_LONGITUDE, Site.COLUMN_ADRESSE, Site.COLUMN_CATEGORIE, Site.COLUMN_RESUME};

        int[] textView = {R.id.id, R.id.nom, R.id.latitude, R.id.longitude, R.id.adresse, R.id.categorie, R.id.resume};
        Uri mContacts = SiteDatabaseHelper.CONTENT_URI;
        Cursor cur = managedQuery(mContacts, columns, null, null, null);

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
                ConsultationBDD.this.startActivity(intentConsultation);
                return true;
            case R.id.ajout:
                Intent intentAjout = new Intent(ConsultationBDD.this, AjoutBDD.class);
                ConsultationBDD.this.startActivity(intentAjout);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}