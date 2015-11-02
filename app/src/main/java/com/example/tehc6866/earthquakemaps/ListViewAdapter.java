package com.example.tehc6866.earthquakemaps;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TEHC6866 on 30/10/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;

    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.col_row, null);

            txtFirst=(TextView) convertView.findViewById(R.id.mag);
            txtSecond=(TextView) convertView.findViewById(R.id.place);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get("FIRST"));
        txtSecond.setText(map.get("SECOND"));
        float myMag = Float.valueOf(map.get("FIRST"));
        if (myMag < 3){
            txtFirst.setTextColor(Color.GREEN);
        } else if( myMag < 6){
            txtFirst.setTextColor(Color.YELLOW);
        } else {
            txtFirst.setTextColor(Color.RED);
        }
        return convertView;
    }

}
