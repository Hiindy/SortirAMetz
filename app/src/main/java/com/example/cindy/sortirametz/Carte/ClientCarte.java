package com.example.cindy.sortirametz.Carte;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cindy.sortirametz.BDD.Site;
import com.example.cindy.sortirametz.BDD.SiteDatabaseHelper;
import com.example.cindy.sortirametz.ConsultationBDD;
import com.example.cindy.sortirametz.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

public class ClientCarte extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_carte);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ajouterSite();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Demande d'autourisations
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        // On affiche le calque Ma Position
        mMap.setMyLocationEnabled(true);

        //On cherche la dernière positio
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            // On déplace la caméra sur la position de l'utilisateur
                            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 30));
                            CircleOptions circleOptions = new CircleOptions()
                                    .center(position)
                                    .radius(200)
                                    .strokeWidth(1)
                                    .strokeColor(Color.RED);
                            mMap.addCircle(circleOptions);
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_client_carte_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.consultation:
                Intent intentMain = new Intent(ClientCarte.this, ConsultationBDD.class);
                ClientCarte.this.startActivity(intentMain);
                return true;
            case R.id.modification:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void ajouterSite() {
        // On crée un ContentValues pour ajouter les différents attributs
        ContentValues values = new ContentValues();
        values.put(Site.COLUMN_NOM, "Temple Neuf");
        values.put(Site.COLUMN_LATITUDE, 15482);
        values.put(Site.COLUMN_LONGITUDE, 65);
        values.put(Site.COLUMN_ADRESSE, "blable");
        values.put(Site.COLUMN_CATEGORIE, "religieux");
        values.put(Site.COLUMN_RESUME, "ceci est un test");

        getContentResolver().insert(SiteDatabaseHelper.CONTENT_URI, values);
        values.clear();

        values.put(Site.COLUMN_NOM, "Cathédrale de Metz");
        values.put(Site.COLUMN_LATITUDE, 14.58);
        values.put(Site.COLUMN_LONGITUDE, 65.78);
        values.put(Site.COLUMN_ADRESSE, "deuxième");
        values.put(Site.COLUMN_CATEGORIE, "religieux");
        values.put(Site.COLUMN_RESUME, "ceci est un deuxième test");
        getContentResolver().insert(SiteDatabaseHelper.CONTENT_URI, values);
    }


}
