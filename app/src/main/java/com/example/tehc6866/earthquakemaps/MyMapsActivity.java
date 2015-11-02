package com.example.tehc6866.earthquakemaps;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MyMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private FragmentActivity mapActivity;
    private GoogleMap mMap;
    private float latitude;
    private float longitude;
    private MainProperties myProperties;
    private TextView mytv;
    FloatingActionButton myBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_maps);

        mapActivity = this;

        mytv = (TextView)findViewById(R.id.textView);
        myBtn = (FloatingActionButton) findViewById(R.id.fab);

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapActivity.finish();

            }
        });

        Bundle extras = getIntent().getExtras();
        String inputString = extras.getString("lat");
        latitude = Float.valueOf(inputString);
        inputString = extras.getString("lon");
        longitude = Float.valueOf(inputString);

        myProperties = new MainProperties(extras.getString("mag"),extras.getString("place"),extras.getString("time"),"","",extras.getString("tsunami"),extras.getString("type"),null);
        String mymsg =" "+myProperties.getType()+" mag:"+myProperties.getMag();

        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

        mymsg = mymsg + "\n "+ df.format(Long.valueOf(myProperties.getTime()));

        if (myProperties.getTsunami().equals("0")){
            mymsg = mymsg +"\n No tsunami alert";
        }
        else{
            mymsg = mymsg +"\n Tsunami alert";
        }

        mytv.setText(mymsg);

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
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title(myProperties.getPlace()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

    }
}
