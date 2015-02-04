package com.example.mattia.map2;


import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {
    GoogleMap mMap;
    GMapV2Direction md;

    LatLng fromPosition = new LatLng(39.297694, 9.212160);
    LatLng toPosition = new LatLng(39.296398, 9.210594);




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        md = new GMapV2Direction();
        mMap = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        LatLng coordinates = new LatLng(39.296502, 9.212464);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 16));


        mMap.addMarker(new MarkerOptions().position(fromPosition).title("Start"));
        mMap.addMarker(new MarkerOptions().position(toPosition).title("End"));

        Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_WALKING);
        int duration = md.getDurationValue(doc);
        String distance = md.getDistanceText(doc);
        String start_address = md.getStartAddress(doc);
        String copy_right = md.getCopyRights(doc);

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);

        for(int i = 0 ; i < directionPoint.size() ; i++) {
            rectLine.add(directionPoint.get(i));
        }

        mMap.addPolyline(rectLine);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
