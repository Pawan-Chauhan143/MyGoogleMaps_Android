package com.example.mygooglemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.mygooglemaps.databinding.ActivityGoogleMapWithRuntimeCodeBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapWithRuntimeCode extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    public ActivityGoogleMapWithRuntimeCodeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGoogleMapWithRuntimeCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragement);
        supportMapFragment.getMapAsync(this);

        binding.addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleMap!=null){
                    // 26.684831447644893, 84.09727334690136
                    // 26.68394883906046, 84.0475502290027
                   /* LatLng latLng=new LatLng(26.684831447644893,84.09727334690136);
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(3.0f));
                    CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(latLng,15);
                    mGoogleMap.animateCamera(cameraUpdate, 5000, new GoogleMap.CancelableCallback() {
                        @Override
                        public void onCancel() {
                            Toast.makeText(GoogleMapWithRuntimeCode.this, "animation finished", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(GoogleMapWithRuntimeCode.this, "animation finished", Toast.LENGTH_SHORT).show();
                        }
                    });*/

                    double bottomBoundry=26.684831447644893;
                    double leftBoundry=84.09727334690136;
                    double topBoundry=27.68394883906046;
                    double rightBoundry=85.0475502290027;

                    LatLngBounds latLngBounds=new LatLngBounds(new LatLng(bottomBoundry,leftBoundry),new LatLng(topBoundry,rightBoundry));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,1));
                    showMarker(latLngBounds.getCenter());
                }
            }
        });
    }

    private void showMarker(LatLng latLng) {
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        mGoogleMap.addMarker(markerOptions);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap=googleMap;
        gotoLocation();
    }

    private void gotoLocation() {
        MarkerOptions markerOptions=new MarkerOptions();
        LatLng latLng=new LatLng(26.80395997398446,83.35723646884821);
        markerOptions.position(latLng);
        markerOptions.title("Hello Pawan you are here...");
        mGoogleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,10);
        mGoogleMap.moveCamera(cameraUpdate);
        //mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
       /* mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);*/
        mGoogleMap.setBuildingsEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_menu,menu);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int a=item.getItemId();
        switch (a){
            case R.id.maptype_none:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            }
            case R.id.maptype_normal:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            }
            case R.id.maptype_satelite:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            }
            case R.id.maptype_terrain:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            }
            case R.id.maptype_hybrid:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            } default:{
                Toast.makeText(this, "Not found ", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}