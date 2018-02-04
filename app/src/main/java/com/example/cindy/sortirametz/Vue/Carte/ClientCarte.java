package com.example.cindy.sortirametz.Vue.Carte;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cindy.sortirametz.BDD.Categorie;
import com.example.cindy.sortirametz.BDD.Site;

import com.example.cindy.sortirametz.Vue.BDD.ConsultationBDD;
import com.example.cindy.sortirametz.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ClientCarte extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnCameraMoveStartedListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    public Location myLocation = new Location("");
    ;
    private float rayon = 50;
    private SupportMapFragment mapFragment;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private CircleOptions circleOptionsRayon;
    private Circle circleRayon;
    private boolean suivreUtilisateur;
    private ArrayList<MarkerOptions> listeMarqueurs;
    private Map<Marker, Site> listeMarqueursBis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On ajoute la vue correspondant à la Google Map
        setContentView(R.layout.activity_client_carte);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listeMarqueurs = new ArrayList<>();
        listeMarqueursBis = new HashMap<>();


        //ajouterSite();
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
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
        suivreUtilisateur = true;
        mMap.setOnCameraMoveStartedListener(this);

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);


        mMap.setInfoWindowAdapter(new InfoWindowSite(this));

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            suivreUtilisateur = false;
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                myLocation = location;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                majCircle();
                majMarqueurs();
                if (suivreUtilisateur)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            }
        }

        ;

    };

    private void majCircle() {
        if (circleOptionsRayon == null) {
            circleOptionsRayon = new CircleOptions()
                    .center(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                    .radius(this.rayon)
                    .strokeWidth(1)
                    .strokeColor(Color.GREEN)
                    .fillColor(0x5500ff00);
            circleRayon = mMap.addCircle(circleOptionsRayon);
        } else {
            circleRayon.setCenter(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
            circleRayon.setRadius(this.rayon);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_carte, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bdd:
                Intent intentMain = new Intent(ClientCarte.this, ConsultationBDD.class);
                intentMain.putExtra("positionLatitude", this.myLocation.getLatitude());
                intentMain.putExtra("positionLongitude", this.myLocation.getLongitude());
                ClientCarte.this.startActivity(intentMain);
                return true;
            case R.id.rayon:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Quel rayon ");

                // Create EditText for entry
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);

                // Make an "OK" button to save the name
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Grab the EditText's input
                        String rayon = input.getText().toString();
                        setRayon(Float.parseFloat(rayon));
                    }
                });

                // Make a "Cancel" button
                // that simply dismisses the alert
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }


    public float getRayon() {
        return rayon;
    }

    public void setRayon(float rayon) {
        this.rayon = rayon;
    }

    public void majMarqueurs() {
        Cursor curseurSite = Site.getAllSite(getContentResolver());
        if (curseurSite != null) {
            try {
                while (curseurSite.moveToNext()) {
                    Site site = new Site(
                            curseurSite.getString(curseurSite.getColumnIndex(Site.COLUMN_NOM)),
                            Double.parseDouble(curseurSite.getString(curseurSite.getColumnIndex(Site.COLUMN_LATITUDE))),
                            Double.parseDouble(curseurSite.getString(curseurSite.getColumnIndex(Site.COLUMN_LONGITUDE))),
                            curseurSite.getString(curseurSite.getColumnIndex(Site.COLUMN_ADRESSE)),
                            new Categorie(),
                            curseurSite.getString(curseurSite.getColumnIndex(Site.COLUMN_RESUME))
                    );
                    // On recherche la catégorie
                    for (int i = 0; i < new Categorie().getListeCategories().size(); i++) {
                        if (Categorie.listeCategories.get(i).getLibelle().equals(curseurSite.getString(curseurSite.getColumnIndex(Site.COLUMN_CATEGORIE)))) {
                            site.setCategorie(Categorie.listeCategories.get(i));
                        }
                    }

                    Marker marqueurSite = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(site.getLatitude(), site.getLongitude()))
                            .title(site.getNom())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                            .snippet(site.getAdresse() + "/"
                                    + site.getResume() + "/"
                                    + "(" + site.getLatitude() + "," + site.getLongitude() + ")" + "/"
                                    + site.getCategorie().getImage())
                    );

                    listeMarqueursBis.put(marqueurSite, site);
                }
            } finally {
                afficherMarqueurs();
                curseurSite.close();
            }
        }

    }

    private void afficherMarqueurs() {
        if (!listeMarqueursBis.isEmpty()) {
            Set<Map.Entry<Marker, Site>> setHm = listeMarqueursBis.entrySet();
            Iterator<Map.Entry<Marker, Site>> it = setHm.iterator();
            while (it.hasNext()) {
                Map.Entry<Marker, Site> e = it.next();


                Location locationSite = new Location("Site");
                locationSite.setLatitude(e.getValue().getLatitude());
                locationSite.setLongitude(e.getValue().getLongitude());
                if (myLocation.distanceTo(locationSite) < this.rayon) {
                    e.getKey().setVisible(true);
                } else {
                    e.getKey().setVisible(false);
                }


            }
        }
    }


    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 Secondes d'intervales
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ClientCarte.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        majCircle();
        majMarqueurs();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    suivreUtilisateur = true;

                    return false;
                }
            };

}
