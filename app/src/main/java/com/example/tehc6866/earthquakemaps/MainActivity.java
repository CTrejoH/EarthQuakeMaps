package com.example.tehc6866.earthquakemaps;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static String urlString;

    private ArrayList<MainProperties> propertiesList = new ArrayList<MainProperties>();
    private ArrayList<HashMap<String, String>> list;
    private ArrayList<HashMap<String, String>> cacheList;
    private ListView listView;
    private ListViewAdapter adapter;

    private FloatingActionButton myBtn;
    private FloatingActionButton myBtnAllMaps;

    private AppCompatActivity myActivity;

    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myActivity = this;

        myBtn = (FloatingActionButton) findViewById(R.id.fab);

        myBtnAllMaps = (FloatingActionButton) findViewById(R.id.allMap);

        listView=(ListView)findViewById(R.id.listView1);
        list = new ArrayList<HashMap<String,String>>();
        cacheList = new ArrayList<HashMap<String,String>>();

        adapter=new ListViewAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                int pos = position;

                MainProperties myProperties = propertiesList.get(pos);
                Intent i = new Intent(myActivity, MyMapsActivity.class);
                i.putExtra("mag", myProperties.getMag());
                i.putExtra("place", myProperties.getPlace());
                i.putExtra("time", myProperties.getTime());
                i.putExtra("tsunami", myProperties.getTsunami());
                i.putExtra("type", myProperties.getType());
                i.putExtra("lon", String.valueOf(myProperties.getGeoPoint().getLongitude()));
                i.putExtra("lat", String.valueOf(myProperties.getGeoPoint().getLatitude()));
                i.putExtra("depth", String.valueOf(myProperties.getGeoPoint().getDepth()));

                startActivityForResult(i, REQUEST_CODE);
            }

        });


        urlString = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
        new ProcessJSON().execute(urlString);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cacheList.addAll(list);
                list.clear();
                adapter.notifyDataSetChanged();
                //  Toast.makeText(myActivity,"Earthquake Data Reloading ...", Toast.LENGTH_LONG).show();
                new ProcessJSON().execute(urlString);
            }
        });
        myBtnAllMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(myActivity, AllMapsActivity.class);
                int member = 0;
                for (MainProperties elem:propertiesList) {
                    i.putExtra("key-"+String.valueOf(member),elem.getPlace());
                    i.putExtra("key-"+String.valueOf(member+1),elem.getMag());
                    i.putExtra("key-"+String.valueOf(member+2),String.valueOf(elem.getGeoPoint().getLatitude()));
                    i.putExtra("key-"+String.valueOf(member+3),String.valueOf(elem.getGeoPoint().getLongitude()));
                    member= member + 4;
                }
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    private class ProcessJSON extends AsyncTask<String, Void, String> {




        protected String doInBackground(String... strings){
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);
            // Return the data from specified url
            return stream;



        }

        protected void onPostExecute(String stream){
            /*
                Important in JSON DATA
                -------------------------
                * Square bracket ([) represents a JSON array
                * Curly bracket ({) represents a JSON object
                * JSON object contains key/value pairs
                * Each key is a String and value may be different data types
             */

            //..........Process JSON DATA................
            if(stream !=null){
                try{
                    // Get the full HTTP Data as JSONObject
                    JSONObject reader= new JSONObject(stream);

                    // Get the JSONObject "coord"...........................
                    JSONArray featureArray = reader.getJSONArray("features");

                    for (int ind = 0;ind < featureArray.length();ind++){
                        JSONObject feauterDetail = featureArray.getJSONObject(ind);
                        String id = feauterDetail.optString("id");

                        JSONObject geometryDetail = feauterDetail.getJSONObject("geometry");
                        JSONArray coord = geometryDetail.getJSONArray("coordinates");
                        GPoint myPoint = new GPoint(Float.valueOf(coord.getString(0)),Float.valueOf(coord.getString(1)),Float.valueOf(coord.getString(2)));
                        JSONObject propertiesDetail = feauterDetail.getJSONObject("properties");
                        String magDetail = propertiesDetail.getString("mag");
                        String placeDetail = propertiesDetail.getString("place");
                        MainProperties myProperties = new MainProperties(
                                propertiesDetail.getString("mag"),
                                propertiesDetail.getString("place"),
                                propertiesDetail.getString("time"),
                                propertiesDetail.getString("updated"),
                                propertiesDetail.getString("alert"),
                                propertiesDetail.getString("tsunami"),
                                propertiesDetail.getString("type"),
                                myPoint);
                        propertiesList.add(myProperties);
                        HashMap<String,String> temp=new HashMap<String, String>();
                        temp.put("FIRST", magDetail);
                        temp.put("SECOND", placeDetail);
                        list.add(temp);

                        adapter.notifyDataSetChanged();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            } // if statement end
            else{
                // next setence just applies if there is not data and old data is in cache
                if (!cacheList.isEmpty()) {
                    list.addAll(cacheList);

                }
            }
        } // onPostExecute() end
    } // ProcessJSON class end
}


