package edu.nyu.xiaoqianyu.googlemaps;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity {

    private GoogleMap googleMap;
    private StreetViewPanorama mapStreetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMapView();
        addMarker();
        createStreetView();


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(mapStreetView != null) {
                    Fragment mapView = getFragmentManager().findFragmentById(R.id.mapView);
                    getFragmentManager().beginTransaction().hide(mapView).commit();

                    mapStreetView.setPosition(latLng);
                }
                else {
                    System.out.println("Map street view is not initiated.");
                }
            }
        });
    }

    private void createStreetView() {
        mapStreetView = ((StreetViewPanoramaFragment)getFragmentManager().findFragmentById(R.id.streetView)).getStreetViewPanorama();
    }

    private void createMapView() {
        try {
            if(null == googleMap) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
            }
            if(null == googleMap) {
                Toast.makeText(getApplicationContext(), "Error Alert: While creating a Map", Toast.LENGTH_SHORT).show();
            }
        } catch(NullPointerException e) {
            Log.e("mapApp", e.toString());
        }
    }

    private void addMarker() {
        if(null != googleMap) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(40.829342, -73.42342)).title("Hey asfa!").draggable(true));
        }
    }
}
