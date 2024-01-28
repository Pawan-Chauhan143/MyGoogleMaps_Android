package com.example.mygooglemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mygooglemaps.databinding.ActivityGoogleMapsShowOnFragmentBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsShowOnFragment extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityGoogleMapsShowOnFragmentBinding binding=null;
    GoogleMap mGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGoogleMapsShowOnFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // work with the fragment and its define compile time
         SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
         supportMapFragment.getMapAsync(this);

         // work with the frameLayout and its define runtime time
         /* SupportMapFragment supportMapFragment=SupportMapFragment.newInstance();
         getSupportFragmentManager().beginTransaction()
                .add(R.id.mapFragment_container,supportMapFragment)
                .commit();
        supportMapFragment.getMapAsync(this);*/
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("MyApp","onMapReady: Map Shown on Screen");
        mGoogleMap=googleMap;

        MarkerOptions markerOptions=new MarkerOptions()
                        .title("Hello Pawan You are here..")
                .position(new LatLng(0,0));
        mGoogleMap.addMarker(markerOptions);
    }
}