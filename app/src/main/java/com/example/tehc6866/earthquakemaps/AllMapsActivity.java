package com.example.tehc6866.earthquakemaps;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AllMapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private ArrayList<GeoPoint> myList = new ArrayList<GeoPoint>();
    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_maps);
        Bundle b = getIntent().getExtras();

        if(b!=null){
            for (int x=0;x<b.size();x=x+4){
                String strPlace = b.getString("key-"+(x));
                String strMag = b.getString("key-"+(x+1));
                String strLat = b.getString("key-"+(x+2));
                String strLon = b.getString("key-"+(x+3));
                String tColor;
                if (Float.valueOf(strMag) < 3){
                    tColor = "GREEN";
                } else if( Float.valueOf(strMag) < 6){
                    tColor = "YELLOW";
                } else {
                    tColor = "RED";
                }
                GeoPoint myPoint = new GeoPoint(strPlace,tColor,Float.valueOf(strLat),Float.valueOf(strLon));
                myList.add(myPoint);
            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        for (GeoPoint elem:myList){
            LatLng location = new LatLng(elem.getLat(), elem.getLon());
            if (elem.getColor().equals("GREEN")){
                mMap.addMarker(new MarkerOptions().position(location).title(elem.getPlace()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            } else if (elem.getColor().equals("YELLOW")){
                mMap.addMarker(new MarkerOptions().position(location).title(elem.getPlace()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            } else {
                mMap.addMarker(new MarkerOptions().position(location).title(elem.getPlace()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

    }
    private class GeoPoint{
        String place;
        String color;
        float lon;
        float lat;

        public GeoPoint(String place, String color,float lat, float lon) {
            this.place = place;
            this.color = color;
            this.lon = lon;
            this.lat = lat;
        }

        public String getPlace() { return place; }

        public String getColor() {
            return color;
        }

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }
    }
}
