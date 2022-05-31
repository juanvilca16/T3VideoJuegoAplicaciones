package com.example.app_sf;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.app_sf.databinding.ActivityMapsBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    //origin and destination
    private Marker mMarkerFrom ;
    private Marker mMarkerTO ;
    private LatLng mFromLatLng;
    private LatLng mToLatLng;
    private ActivityMapsBinding binding;
    private int requestCode;
    private final int REQUEST_CODE_AUTOCOMPLETE_FROM = 1;
    private final int REQUEST_CODE_AUTOCOMPLETE_TO = 2;
    Button btn1From, btn2TO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Places.initialize(getApplicationContext(), "AIzaSyCEYFx-yEZcaQp_LkSwhl4f_C3vP6ogM-Y");

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocalizacion();

        btn1From = findViewById(R.id.btnFrom);
        btn2TO = findViewById(R.id.btnTo);

        btn1From.setOnClickListener(this);
        btn2TO.setOnClickListener(this);
    }

    private void startAutocomplete(int requestCod){
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent , requestCod);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_FROM) {
            processAutocompleteResult(resultCode, data, requestCode);

            if(mFromLatLng!=null){
                setMarkerFrom(mFromLatLng);
            }

            return;
        }else if(requestCode == REQUEST_CODE_AUTOCOMPLETE_TO) {
            processAutocompleteResult(resultCode, data, requestCode);

            if(mToLatLng!=null){
                setMarkerTo(mToLatLng);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processAutocompleteResult(int resultCode, @Nullable Intent data, int requestCode){
        LatLng latLng;
        if (resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            latLng = place.getLatLng();
            if(requestCode == 1){
                mFromLatLng = latLng;
            }else if(requestCode == 2){
                mToLatLng = latLng;
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // TODO: Handle the error.
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.i("MapsActivity", status.getStatusMessage());
        }

    }

       public void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permiso == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
            }
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        LocationManager locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(miUbicacion).title("ubicacion actual"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(miUbicacion)
                        .zoom(14)
                        .bearing(90)
                        .tilt(45)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    private Marker addMarker(LatLng latLng, String title) {
        MarkerOptions MarkerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        return mMap.addMarker(MarkerOptions);
    }

    private void setMarkerFrom(LatLng latLng){
        if(mMarkerFrom != null){
            mMarkerFrom.remove();
        }
        mMarkerFrom = addMarker(latLng,"Origen");
    }

    private void setMarkerTo(LatLng latLng){
        if(mMarkerTO != null){
            mMarkerTO.remove();
        }
        mMarkerTO = addMarker(latLng,"Destino");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnFrom){
            startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_FROM);
        }else if (id == R.id.btnTo){
            startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_TO);
        }
    }
}/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-12, -77);
        //Marca de posicion
        mMap.addMarker(new MarkerOptions().position(sydney).title("Lima"));
        //Tipo de mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Zoom del mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 30));

        LatLng s = new LatLng(-14, -78);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource
             (R.mipmap.ic_one)).anchor(-15,-79).position(s));
    }
}*/