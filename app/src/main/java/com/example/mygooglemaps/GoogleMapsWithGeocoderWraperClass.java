package com.example.mygooglemaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.mygooglemaps.databinding.ActivityGoogleMapsWithGeocoderWraperClassBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapsWithGeocoderWraperClass extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mgoogleMap;
    ActivityGoogleMapsWithGeocoderWraperClassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGoogleMapsWithGeocoderWraperClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gpsEnabled();
        SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragement);
        supportMapFragment.getMapAsync(this);
        binding.searchView.setOnClickListener(this::geoLocate);
    }

    private void geoLocate(View view) {
        hideSoftKeyboard(view);
        String locationName=binding.editTextText.getText().toString();
        Geocoder geocoder=new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressesList=geocoder.getFromLocationName(locationName,4);
            if (addressesList.size()>0){
                Address address=addressesList.get(0);
                gotoLocation(address.getLatitude(),address.getLongitude());
                //mgoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));
                Toast.makeText(this, "geoLocate:Country "+ address.getLocality(), Toast.LENGTH_SHORT).show();
                Log.d("TAG","geoLocate:Country "+ address.getLocality());
            }
            for(Address address:addressesList){
                Log.d("TAG","geoLocate:Country "+ address.getAddressLine(address.getMaxAddressLineIndex()));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean gpsEnabled(){
        LocationManager locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnabled){
            return true;
        }else{
            AlertDialog alertDialog=new AlertDialog.Builder(this)
                    .setTitle("GPS Permisssion")
                    .setMessage("GPS is required for this app to work. Please enabled GPS")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent,1001);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1001){
            LocationManager locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (providerEnabled){
                Toast.makeText(this, "GPS Enabled..", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "GPS not enabled..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng=new LatLng(latitude,longitude);
        MarkerOptions markerOptions=new MarkerOptions()
                .title("Hello Pawan you are here...")
                .position(latLng);
        mgoogleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,15);
        mgoogleMap.moveCamera(cameraUpdate);

    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mgoogleMap=googleMap;
        //mgoogleMap.setMyLocationEnabled(true);
        mgoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mgoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mgoogleMap.getUiSettings().setMapToolbarEnabled(true);
    }
}